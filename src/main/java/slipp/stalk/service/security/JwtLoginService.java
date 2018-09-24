package slipp.stalk.service.security;

import org.springframework.stereotype.Component;
import slipp.stalk.controller.exceptions.BadCredentialsException;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.infra.jpa.repository.MemberRepository;

@Component
public class JwtLoginService {

    private final MemberRepository repository;
    private final JwtHelper jwtHelper;

    public JwtLoginService(MemberRepository repository, JwtHelper jwtHelper) {
        this.repository = repository;
        this.jwtHelper = jwtHelper;
    }

    public JwtToken login(String email, String password) {
        Member member = repository.findByEmail(email)
                                  .orElseThrow(MemberNotFoundException::new);
        checkPassword(member, password);
        return createJwtTokenFromMember(member);
    }

    private void checkPassword(Member member, String password) {
        if (!member.checkPassword(password)) {
            throw new BadCredentialsException();
        }
    }

    private JwtToken createJwtTokenFromMember(Member member) {
        return jwtHelper.createToken(member);
    }
}