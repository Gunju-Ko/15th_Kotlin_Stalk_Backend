package slipp.stalk.service.messaging;

import lombok.Data;

@Data
public class Response {
    private final Result result;
    private final String detailMessage;

    public Response(Result result, String detailMessage) {
        this.result = result;
        this.detailMessage = detailMessage;
    }

    public enum Result {
        SUCCESS,
        FAIL
    }
}
