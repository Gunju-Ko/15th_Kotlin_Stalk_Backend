package slipp.stalk.service.member;

import slipp.stalk.domain.Member;

public interface CreateMemberListener {
    void created(Member member);
}
