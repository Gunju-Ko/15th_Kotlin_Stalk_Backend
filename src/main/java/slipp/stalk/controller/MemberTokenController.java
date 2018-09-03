package slipp.stalk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.controller.dto.TokenDto;
import slipp.stalk.service.MemberService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members/{id}")
public class MemberTokenController {

    private final MemberService memberService;

    public MemberTokenController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/token")
    public ResponseEntity<Void> registerToken(@PathVariable long id,
                                              @RequestBody @Valid TokenDto token) {
        memberService.registerToken(id, token.getToken());
        return ResponseEntity.noContent()
                             .build();
    }
}
