package slipp.stalk.service.message;

import java.util.List;

public interface ResponseHandler {
    void handleResponse(List<Response> responses);
}
