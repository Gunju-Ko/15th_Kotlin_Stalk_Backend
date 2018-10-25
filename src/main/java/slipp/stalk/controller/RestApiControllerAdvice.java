package slipp.stalk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.controller.exceptions.HttpStatusException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestApiControllerAdvice {

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ResponseDto<Object>> handleCustomException(HttpStatusException e) {
        return ResponseEntity.status(e.httpStatus())
                             .body(ResponseDto.fromErrorMessage(e.errorMessage()));

    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Object>> handleInternalServerError(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ResponseDto.fromErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler({ConstraintViolationException.class,
                       MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto<Object>> handlerConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ResponseDto.fromErrorMessage(ex.getMessage()));
    }
}
