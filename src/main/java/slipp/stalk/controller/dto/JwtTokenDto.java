package slipp.stalk.controller.dto;

import lombok.Data;
import org.springframework.util.StringUtils;
import slipp.stalk.service.security.JwtToken;

@Data
public class JwtTokenDto {
    private String token;

    private JwtTokenDto() {
    }

    private JwtTokenDto(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("JWT token cannot be empty");
        }
        this.token = token;
    }

    public static JwtTokenDto of(JwtToken token) {
        return new JwtTokenDto(token.getToken());
    }
}
