package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends HttpStatusException {

    private static final String MESSAGE = "해당 자원을 찾을수 없습니다";

    @Override
    public final HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String errorMessage() {
        return MESSAGE;
    }
}
