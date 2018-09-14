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

    public boolean isFailed() {
        return result == Result.FAIL;
    }

    public enum Result {
        SUCCESS,
        FAIL
    }
}
