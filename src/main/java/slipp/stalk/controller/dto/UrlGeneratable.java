package slipp.stalk.controller.dto;

import org.springframework.http.HttpHeaders;

import java.net.URI;

public interface UrlGeneratable {
    URI generateApiUri();

    default HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(generateApiUri());

        return headers;
    }
}
