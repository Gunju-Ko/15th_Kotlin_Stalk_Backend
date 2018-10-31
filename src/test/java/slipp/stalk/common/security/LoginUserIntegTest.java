package slipp.stalk.common.security;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.integration.api.IntegTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LoginUserIntegTest extends IntegTest {

    @Test
    public void given_loginUser__when_loginIsRequired_should_return_200() {
        String userEmail = "gunjuko92@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(userEmail,
                                                                   "/users/loginrequired",
                                                                   new ParameterizedTypeReference<ResponseDto<MemberDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertThat(response.getData().getEmail()).isEqualTo(userEmail);
    }

    // 로그인 기능전까지는 기본 유저를 리턴하도록 구현
    @Test
    @Ignore
    public void given_notExistUser__when_loginIsRequired_should_return_404() {
        String userEmail = "gunjuko921201@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/loginrequired", MemberDto.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
    }

    // 로그인 기능전까지는 기본 유저 리턴한도록 구현
    @Test
    @Ignore
    public void given_withoutLogin__when_loginIsRequired_should_return_404() {
        ResponseEntity<MemberDto> response = restTemplate().getForEntity("/users/loginrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void given_loginUser__when_loginIsNotRequired_should_return_200() {
        String userEmail = "gunjuko92@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(userEmail,
                                                                   "/users/notrequired",
                                                                   new ParameterizedTypeReference<ResponseDto<MemberDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertThat(response.getData().getEmail()).isEqualTo(userEmail);
    }

    // 로그인 기능전까지는 기본 유저를 리턴하도록 구현
    @Test
    @Ignore
    public void given_notExistUser__when_loginIsNotRequired_should_return_404() {
        String userEmail = "gunjuko921201@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(userEmail, "/users/notrequired", MemberDto.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
    }

    @Test
    public void given_withoutLogin__when_loginIsNotRequired_should_return_200() {
        ResponseEntity<MemberDto> response = restTemplate().getForEntity("/users/notrequired", MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void given_adminUser__when_adminIsRequired_should_return_200() {
        String adminEmail = "gunjuko92@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(adminEmail,
                                                                   "/admin/loginrequired",
                                                                   new ParameterizedTypeReference<ResponseDto<MemberDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertThat(response.getData().getEmail()).isEqualTo(adminEmail);
    }

    @Test
    public void given_loginUser__when_adminIsRequired_should_return_403() {
        String userEmail = "minhwan@gmail.com";
        ResponseDto<MemberDto> response = getForEntityWithJwtToken(userEmail, "/admin/loginrequired", MemberDto.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
    }

}
