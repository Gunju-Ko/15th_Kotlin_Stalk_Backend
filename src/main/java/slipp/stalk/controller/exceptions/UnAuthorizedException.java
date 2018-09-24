package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends HttpStatusException {
    private final String message;

    public UnAuthorizedException(String message) {
        this.message = message;
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
