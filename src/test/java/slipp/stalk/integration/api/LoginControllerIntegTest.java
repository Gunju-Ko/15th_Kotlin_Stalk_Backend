package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.JwtTokenDto;
import slipp.stalk.controller.dto.LoginInfoDto;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginControllerIntegTest extends IntegTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should__return_jwt_token_when_login_success() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        ResponseEntity<JwtTokenDto> response = restTemplate.postForEntity("/login", createBody(email, password), JwtTokenDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldNot__return_jwt_token_when_password_is_too_short() {
        String email = "gunjuko92@gmail.com";
        String password = "test";
        ResponseEntity<JwtTokenDto> response = restTemplate.postForEntity("/login", createBody(email, password), JwtTokenDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNot__return_jwt_token_when_password_is_wrong() {
        String email = "gunjuko92@gmail.com";
        String wrongPassword = "test1234";
        ResponseEntity<JwtTokenDto> response = restTemplate.postForEntity("/login", createBody(email, wrongPassword), JwtTokenDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private LoginInfoDto createBody(String email, String password) {
        LoginInfoDto body = new LoginInfoDto();
        body.setEmail(email);
        body.setPassword(password);
        return body;
    }
}
