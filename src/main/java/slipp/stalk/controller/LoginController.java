package slipp.stalk.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.JwtTokenDto;
import slipp.stalk.controller.dto.LoginInfoDto;
import slipp.stalk.controller.dto.LogoutDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.domain.Member;
import slipp.stalk.service.security.JwtLoginService;
import slipp.stalk.service.security.JwtToken;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final JwtLoginService jwtLoginService;

    public LoginController(JwtLoginService jwtLoginService) {
        this.jwtLoginService = jwtLoginService;
    }

    @PostMapping("/login")
    public ResponseDto<JwtTokenDto> login(@RequestBody @Valid LoginInfoDto loginInfo) {
        JwtToken token = jwtLoginService.login(loginInfo.getEmail(), loginInfo.getPassword(), loginInfo.getToken());
        return ResponseDto.ok(JwtTokenDto.of(token));
    }

    @PostMapping("/logout")
    public ResponseDto<Void> logout(@RequestBody @Valid LogoutDto logoutDto,
                                    @LoginUser Member loginMember) {
        jwtLoginService.logout(loginMember.getId(), logoutDto.getToken());
        return ResponseDto.noContent();
    }

}
