package slipp.stalk.service.security;

import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;
import slipp.stalk.service.member.MemberService;

@Component
public class JwtLoginService {

    private final MemberService memberService;
    private final JwtHelper jwtHelper;

    public JwtLoginService(MemberService memberService, JwtHelper jwtHelper) {
        this.memberService = memberService;
        this.jwtHelper = jwtHelper;
    }

    public JwtToken login(String email, String password, String token) {
        Member member = memberService.getLoginMember(email, password);
        memberService.registerToken(member.getId(), token);
        return createJwtTokenFromMember(member);
    }

    private JwtToken createJwtTokenFromMember(Member member) {
        return jwtHelper.createToken(member);
    }
}