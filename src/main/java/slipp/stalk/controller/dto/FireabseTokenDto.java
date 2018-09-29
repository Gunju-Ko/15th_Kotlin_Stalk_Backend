package slipp.stalk.controller.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;

@Data
public class FireabseTokenDto {

    @NotEmpty
    private String token;

    public FireabseTokenDto() {
    }

    private FireabseTokenDto(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Firebase token cannot be empty");
        }
        this.token = token;
    }

    public static FireabseTokenDto of(String token) {
        return new FireabseTokenDto(token);
    }
}
