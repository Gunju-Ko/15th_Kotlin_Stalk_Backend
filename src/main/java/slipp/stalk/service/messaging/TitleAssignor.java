package slipp.stalk.service.messaging;

import slipp.stalk.domain.Member;

public interface TitleAssignor {
    String makeTitle(Member from, Member to);
}
