package slipp.stalk.controller.exceptions;

import org.springframework.util.StringUtils;

public class MessageAlreadyExistException extends ConflictException {

    private final String message;

    public MessageAlreadyExistException(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        this.message = message;
    }

    @Override
    public String errorMessage() {
        return String.format("%s는 이미 존재하는 메시지 입니다.", message);
    }
}
