package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends HttpStatusException {

    private static final String MESSAGE = "The request could not be completed due to a conflict with the current state of the target " +
        "resource";

    @Override
    public final HttpStatus httpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String errorMessage() {
        return MESSAGE;
    }
}
