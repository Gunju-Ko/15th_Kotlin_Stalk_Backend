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

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberTokenControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void registerToken() throws Exception {
        TokenDto token = new TokenDto("test");
        long memberId = 1;

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<String> tokens = member.getTokens().stream()
                                    .map(Token::getToken)
                                    .collect(Collectors.toList());

        assertThat(tokens.contains(token.getToken())).isTrue();

        // TODO : delete resource
    }

    @Test
    public void registerToken_fail_if_token_already_exist() throws Exception {

    }

    private String createUrl(long memberId) {
        return String.format("/members/%s/token", memberId);
    }

}
