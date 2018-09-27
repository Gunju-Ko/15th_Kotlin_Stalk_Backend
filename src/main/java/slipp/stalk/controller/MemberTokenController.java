package slipp.stalk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.FireabseTokenDto;
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

    @PostMapping("/tokens")
    public ResponseEntity<Void> registerToken(@LoginUser Member member,
                                              @RequestBody @Valid FireabseTokenDto token) {
        memberService.registerToken(member.getId(), token.getToken());
        return ResponseEntity.noContent()
                             .build();
    }

    @DeleteMapping("/tokens")
    public ResponseEntity<Void> deleteToken(@LoginUser Member member,
                                            @RequestBody @Valid FireabseTokenDto token) {
        memberService.deleteToken(member.getId(), token.getToken());
        return ResponseEntity.noContent()
                             .build();
    }
}
