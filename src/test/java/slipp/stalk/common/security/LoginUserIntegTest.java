package slipp.stalk.common.security;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.integration.api.IntegTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginUserIntegTest extends IntegTest {

    @Test
    public void given_loginUser__when_loginIsRequired_should_return_200() {
        String userEmail = "gunjuko92@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(userEmail);
    }

    @Test
    public void given_notExistUser__when_loginIsRequired_should_return_404() {
        String userEmail = "gunjuko921201@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void given_withoutLogin__when_loginIsRequired_should_return_404() {
        ResponseEntity<MemberDto> response = restTemplate().getForEntity("/users/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void given_loginUser__when_loginIsNotRequired_should_return_200() {
        String userEmail = "gunjuko92@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/notrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(userEmail);
    }

    @Test
    public void given_notExistUser__when_loginIsNotRequired_should_return_404() {
        String userEmail = "gunjuko921201@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/notrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void given_withoutLogin__when_loginIsNotRequired_should_return_200() {
        ResponseEntity<MemberDto> response = restTemplate().getForEntity("/users/notrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void given_adminUser__when_adminIsRequired_should_return_200() {
        String adminEmail = "gunjuko92@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(adminEmail, "/admin/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(adminEmail);
    }

    @Test
    public void given_loginUser__when_adminIsRequired_should_return_403() {
        String userEmail = "minhwan@gmail.com";
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(userEmail, "/admin/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
