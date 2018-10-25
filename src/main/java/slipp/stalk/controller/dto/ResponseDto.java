package slipp.stalk.controller.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto<T> {

    private Result result;
    private T data;
    private String errorMessage;

    private ResponseDto() {
    }

    private ResponseDto(Result result, T data) {
        this.data = data;
        this.result = result;
    }

    public static <V> ResponseDto<V> create(V body, HttpStatus status) {
        return new ResponseDto<>(Result.of(status), body);
    }

    public static <V> ResponseDto<V> ok(V body) {
        return create(body, HttpStatus.OK);
    }

    public static ResponseDto<Void> noContent() {
        return create(null, HttpStatus.NO_CONTENT);
    }

    public static ResponseDto<Object> fromErrorMessage(String errorMessage) {
        ResponseDto<Object> response = new ResponseDto<>(Result.FAIL, null);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public enum Result {
        SUCCESS,
        FAIL;

        public static Result of(HttpStatus httpStatus) {
            if (httpStatus.is2xxSuccessful()) {
                return SUCCESS;
            }
            return FAIL;
        }
    }
}
