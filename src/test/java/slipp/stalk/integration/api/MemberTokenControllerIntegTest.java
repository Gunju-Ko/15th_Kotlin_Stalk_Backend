package slipp.stalk.integration.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import slipp.stalk.controller.dto.FireabseTokenDto;
import slipp.stalk.domain.Token;
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
        FireabseTokenDto token = new FireabseTokenDto("test");
        long id = 1;

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(id), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(memberHasToken(id, token)).isTrue();

        deleteToken(token, id);

        assertThat(memberHasToken(id, token)).isFalse();
    }

    @Test
    public void registerToken_fail_if_token_already_exist() throws Exception {
        FireabseTokenDto token = new FireabseTokenDto("test");
        long id = 1;
        createToken(token, id);

        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(id), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(memberHasToken(id, token)).isTrue();

        deleteToken(token, id);
        assertThat(memberHasToken(id, token)).isFalse();
    }

    private boolean memberHasToken(long id, FireabseTokenDto token) {
        Token t = tokenRepository.findByValue(token.getToken()).orElse(null);
        if (t == null) {
            return false;
        }
        return t.getMember().getId() == id;
    }

    private void createToken(FireabseTokenDto token, long id) {
        ResponseEntity<Void> response = restTemplate.postForEntity(createUrl(id), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void deleteToken(FireabseTokenDto token, long id) {
        ResponseEntity<Void> response = restTemplate.exchange(createUrl(id),
                                                              HttpMethod.DELETE,
                                                              new HttpEntity<>(token),
                                                              Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private String createUrl(long id) {
        return String.format("/members/%s/token", id);
    }

}
