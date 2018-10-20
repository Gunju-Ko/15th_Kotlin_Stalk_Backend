package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.JwtTokenDto;
import slipp.stalk.controller.dto.LoginInfoDto;
import slipp.stalk.controller.dto.LogoutDto;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginControllerIntegTest extends IntegTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void should__return_jwt_token_when_login_success() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";

        ResponseEntity<JwtTokenDto> response = login(email, password, token);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTokenIsExist(token);

        deleteToken(token);
    }

    @Test
    public void should_fail_login_when_firebase_token_already_exist() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";
        // login
        login(email, password, token);

        // login again with same firebase token
        ResponseEntity<JwtTokenDto> response = login(email, password, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteToken(token);
    }

    @Test
    public void shouldNot__return_jwt_token_when_password_is_too_short() {
        String email = "gunjuko92@gmail.com";
        String password = "test";
        String token = "test token";

        ResponseEntity<JwtTokenDto> response = login(email, password, token);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertTokenIsNotExist(token);
    }

    @Test
    public void shouldNot__return_jwt_token_when_password_is_wrong() {
        String email = "gunjuko92@gmail.com";
        String wrongPassword = "test1234";
        String token = "test token";

        ResponseEntity<JwtTokenDto> response = login(email, wrongPassword, token);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertTokenIsNotExist(token);
    }

    @Test
    public void should__delete_token_when_logout_success() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";

        login(email, password, token);
        assertTokenIsExist(token);

        ResponseEntity<Void> response = logout(email, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertTokenIsNotExist(token);
    }

    private ResponseEntity<Void> logout(String email, String token) {
        LogoutDto logoutDto = new LogoutDto(token);
        return postForEntityWithJwtToken(email, "/logout", logoutDto, Void.class);
    }

    private ResponseEntity<JwtTokenDto> login(String email, String password, String token) {
        return restTemplate.postForEntity("/login", createBody(email, password, token), JwtTokenDto.class);
    }

    private void assertTokenIsExist(String token) {
        assertTokenIsExistOrNot(token, true);
    }

    private void assertTokenIsNotExist(String token) {
        assertTokenIsExistOrNot(token, false);
    }

    private void assertTokenIsExistOrNot(String token, boolean exist) {
        assertThat(tokenRepository.findByValue(token).isPresent()).isEqualTo(exist);
    }

    private LoginInfoDto createBody(String email, String password, String token) {
        LoginInfoDto body = new LoginInfoDto();
        body.setEmail(email);
        body.setPassword(password);
        body.setToken(token);

        return body;
    }

    private void deleteToken(String token) {
        Token dbToken = tokenRepository.findByValue(token)
                                       .orElseThrow(IllegalArgumentException::new);
        tokenRepository.delete(dbToken);
    }
}
