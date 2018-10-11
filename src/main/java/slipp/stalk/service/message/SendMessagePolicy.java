package slipp.stalk.service.message;

import slipp.stalk.domain.Member;

public interface SendMessagePolicy {
    boolean canSendMessage(Member from, Member send);
}
