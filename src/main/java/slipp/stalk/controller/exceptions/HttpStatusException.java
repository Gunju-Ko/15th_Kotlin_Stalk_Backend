package slipp.stalk.controller.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HttpStatusException extends RuntimeException {

    public abstract HttpStatus httpStatus();

    public abstract String errorMessage();
}
