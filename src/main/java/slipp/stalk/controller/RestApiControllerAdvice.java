package slipp.stalk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import slipp.stalk.controller.dto.ErrorResponse;
import slipp.stalk.controller.exceptions.HttpStatusException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestApiControllerAdvice {

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(HttpStatusException e) {
        return ResponseEntity.status(e.httpStatus())
                             .body(new ErrorResponse(e.httpStatus(), e.errorMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class,
                       MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerConstraintViolationException(ConstraintViolationException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
