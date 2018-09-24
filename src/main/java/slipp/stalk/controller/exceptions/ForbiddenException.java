package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends HttpStatusException {

    @Override
    public final HttpStatus httpStatus() {
        return HttpStatus.FORBIDDEN;
    }

}
