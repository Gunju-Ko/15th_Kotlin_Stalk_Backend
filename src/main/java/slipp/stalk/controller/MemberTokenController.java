package slipp.stalk.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.FireabseTokenDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.domain.Member;
import slipp.stalk.service.member.MemberService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberTokenController {

    private final MemberService memberService;

    public MemberTokenController(MemberService memberService) {
        this.memberService = memberService;
    }

    @DeleteMapping("/tokens")
    public ResponseDto<Void> deleteToken(@LoginUser Member member,
                                         @RequestBody @Valid FireabseTokenDto token) {
        memberService.deleteToken(member.getId(), token.getToken());
        return ResponseDto.noContent();
    }
}
