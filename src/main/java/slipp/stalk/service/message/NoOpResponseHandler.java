package slipp.stalk.service.message;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoOpResponseHandler implements ResponseHandler {

    @Override
    public void handleResponse(List<Response> responses) {

    }
}
