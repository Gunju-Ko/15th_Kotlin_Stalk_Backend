package slipp.stalk.controller.dto;

import lombok.Data;

import java.net.URI;

@Data
public class MemberDto implements UrlGeneratable {
    private long id;
    private String name;
    private String email;

    @Override
    public URI generateApiUri() {
        return URI.create("/members");
    }
}
