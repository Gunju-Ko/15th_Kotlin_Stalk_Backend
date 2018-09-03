package slipp.stalk.integration.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import slipp.stalk.controller.dto.TokenDto;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.MemberRepository;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberTokenControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void registerToken() throws Exception {
        TokenDto token = new TokenDto("test");
        long memberId = 1;

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Collection<String> tokens = member.getTokens().stream()
                                          .map(Token::getToken)
                                          .collect(Collectors.toSet());
        assertThat(tokens.contains(token.getToken())).isTrue();

        Token dbToken = member.getTokens().stream()
                              .filter(t -> t.getToken().equals(token.getToken()))
                              .findFirst().orElseThrow(IllegalStateException::new);

        tokenRepository.delete(dbToken);
    }

    @Test
    public void registerToken_fail_if_token_already_exist() throws Exception {
        TokenDto token = new TokenDto("test");
        long memberId = 1;
        long tokenId = createToken(token, memberId);

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteToken(tokenId);
    }

    private long createToken(TokenDto token, long memberId) {
        Token t = new Token();
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        t.setToken(token.getToken());
        t.setMember(member);

        return tokenRepository.save(t).getId();
    }

    private void deleteToken(long tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    private String createUrl(long memberId) {
        return String.format("/members/%s/token", memberId);
    }

}
