package slipp.stalk.controller.exceptions;

import slipp.stalk.domain.Member;

public class CannotSendMessageException extends BadRequestException {

    private final String errorMessage;

    public CannotSendMessageException(Member from, Member to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }
        this.errorMessage = String.format("%s에게 메시지를 보낼수 없습니다.", to.getName());
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
