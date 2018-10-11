package slipp.stalk.controller.exceptions;

import slipp.stalk.domain.Member;

public class CannotBeFriendException extends ConflictException {

    private final String message;

    public CannotBeFriendException(Member to, Member from) {
        if (to == null || from == null) {
            throw new IllegalArgumentException();
        }
        this.message = String.format("%s와 %s는 친구가 될 수 없습니다", to.getName(), from.getName());
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
