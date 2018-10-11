package slipp.stalk.controller.exceptions;

import slipp.stalk.domain.Member;

public class RelationNotFoundException extends NotFoundException {

    private final String message;

    public RelationNotFoundException(Member from, Member to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }
        this.message = String.format("%s와 %s 사이에 어떠한 관계도 없습니다", from.getName(), to.getName());
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
