package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.FireabseTokenDto;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTokenControllerIntegTest extends IntegTest {

    private final String defaultUserEmail = "gunjuko92@gmail.com";
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void registerToken() throws Exception {
        FireabseTokenDto token = new FireabseTokenDto("test");

        createToken(token, defaultUserEmail);
        assertThat(memberHasToken(defaultUserEmail, token)).isTrue();

        deleteToken(token, defaultUserEmail);
        assertThat(memberHasToken(defaultUserEmail, token)).isFalse();
    }

    @Test
    public void registerToken_fail_if_token_already_exist() throws Exception {
        FireabseTokenDto token = new FireabseTokenDto("test");
        createToken(token, defaultUserEmail);

        ResponseEntity<Void> response = postForEntityWithJwtToken(defaultUserEmail, createUrl(), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(memberHasToken(defaultUserEmail, token)).isTrue();

        deleteToken(token, defaultUserEmail);
        assertThat(memberHasToken(defaultUserEmail, token)).isFalse();
    }

    private boolean memberHasToken(String email, FireabseTokenDto token) {
        Token t = tokenRepository.findByValue(token.getToken()).orElse(null);
        if (t == null) {
            return false;
        }
        return t.getMember().getEmail().equals(email);
    }

    private void createToken(FireabseTokenDto token, String email) {
        ResponseEntity<Void> response = postForEntityWithJwtToken(email, createUrl(), token, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void deleteToken(FireabseTokenDto token, String email) {
        ResponseEntity<Void> response = deleteForEntityWithJwtToken(email,
                                                                    createUrl(),
                                                                    token,
                                                                    Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private String createUrl() {
        return "/members/tokens";
    }

}
