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
import slipp.stalk.infra.jpa.repository.TokenRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberTokenControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void registerToken() throws Exception {
        TokenDto token = new TokenDto("test");
        long memberId = 1;

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        deleteToken(token.getToken());
    }

    @Test
    public void registerToken_fail_if_token_already_exist() throws Exception {
        TokenDto token = new TokenDto("test");
        long memberId = 1;
        createToken(token, memberId);

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteToken(token.getToken());
    }

    private void createToken(TokenDto token, long memberId) {
        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(memberId), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void deleteToken(String token) {
        // TODO : change to API call
        tokenRepository.deleteById(tokenRepository.findByValue(token).getId());
    }

    private String createUrl(long memberId) {
        return String.format("/members/%s/token", memberId);
    }

}
