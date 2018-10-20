package slipp.stalk.commoon.security;

import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;

@Component
public class RoleManager {

    public boolean hasAuthority(Member member, Member.Role role) {
        if (member == null || role == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (role == Member.Role.ADMIN) {
            return member.isAdmin();
        }
        return true;
    }
}
