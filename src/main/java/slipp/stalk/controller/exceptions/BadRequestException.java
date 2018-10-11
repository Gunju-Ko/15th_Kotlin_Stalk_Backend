package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends HttpStatusException {

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
