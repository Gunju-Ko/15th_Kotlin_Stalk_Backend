package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import slipp.stalk.controller.dto.JwtTokenDto;
import slipp.stalk.controller.dto.LoginInfoDto;
import slipp.stalk.controller.dto.LogoutDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginControllerIntegTest extends IntegTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void should__return_jwt_token_when_login_success() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";

        ResponseDto<JwtTokenDto> response = login(email, password, token);

        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
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
        ResponseDto<JwtTokenDto> response = login(email, password, token);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);

        deleteToken(token);
    }

    @Test
    public void shouldNot__return_jwt_token_when_password_is_wrong() {
        String email = "gunjuko92@gmail.com";
        String wrongPassword = "test1234";
        String token = "test token";

        ResponseDto<JwtTokenDto> response = login(email, wrongPassword, token);

        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
        assertTokenIsNotExist(token);
    }

    @Test
    public void should__delete_token_when_logout_success() {
        String email = "gunjuko92@gmail.com";
        String password = "test123";
        String token = "test token";

        login(email, password, token);
        assertTokenIsExist(token);

        ResponseDto<Void> response = logout(email, token);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertTokenIsNotExist(token);
    }

    private ResponseDto<Void> logout(String email, String token) {
        LogoutDto logoutDto = new LogoutDto(token);
        return postForEntityWithJwtToken(email, "/logout", logoutDto, Void.class);
    }

    private ResponseDto<JwtTokenDto> login(String email, String password, String token) {
        return post("/login", createBody(email, password, token), JwtTokenDto.class);
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
