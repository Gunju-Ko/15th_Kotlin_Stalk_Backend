package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.FireabseTokenDto;
import slipp.stalk.controller.dto.JwtTokenDto;
import slipp.stalk.controller.dto.LoginInfoDto;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTokenControllerIntegTest extends IntegTest {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_delete_token() throws Exception {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";

        login(email, password, token);
        assertThat(memberHasToken(email, token)).isTrue();

        deleteToken(email, token);
        assertThat(memberHasToken(email, token)).isFalse();
    }

    private ResponseEntity<JwtTokenDto> login(String email, String password, String token) {
        return restTemplate.postForEntity("/login", createLoginInfo(email, password, token), JwtTokenDto.class);
    }

    private LoginInfoDto createLoginInfo(String email, String password, String token) {
        LoginInfoDto body = new LoginInfoDto();
        body.setEmail(email);
        body.setPassword(password);
        body.setToken(token);

        return body;
    }

    private boolean memberHasToken(String email, String token) {
        Token t = tokenRepository.findByValue(token).orElse(null);
        if (t == null) {
            return false;
        }
        return t.getMember().getEmail().equals(email);
    }

    private void deleteToken(String email, String token) {
        ResponseEntity<Void> response = deleteForEntityWithJwtToken(email,
                                                                    deleteUrl(),
                                                                    FireabseTokenDto.of(token),
                                                                    Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private String deleteUrl() {
        return "/members/tokens";
    }
}
