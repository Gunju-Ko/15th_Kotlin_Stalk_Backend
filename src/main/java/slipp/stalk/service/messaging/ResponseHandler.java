package slipp.stalk.service.messaging;

import java.util.List;

public interface ResponseHandler {
    void handleResponse(List<Response> responses);
}
