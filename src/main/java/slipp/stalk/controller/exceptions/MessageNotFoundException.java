package slipp.stalk.controller.exceptions;

public class MessageNotFoundException extends NotFoundException {
    @Override
    public String errorMessage() {
        return "존재하지 않는 메시지 입니다";
    }
}
