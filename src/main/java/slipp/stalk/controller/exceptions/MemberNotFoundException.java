package slipp.stalk.controller.exceptions;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 회원입니다";

    @Override
    public String errorMessage() {
        return MESSAGE;
    }
}