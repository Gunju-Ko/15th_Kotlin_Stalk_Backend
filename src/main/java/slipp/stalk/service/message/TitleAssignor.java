package slipp.stalk.service.message;

import slipp.stalk.domain.Member;

public interface TitleAssignor {
    String makeTitle(Member from, Member to);
}
