package slipp.stalk.service.security;

import org.springframework.util.StringUtils;

public class JwtToken {
    private String token;

    public JwtToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token cannot be empty");
        }
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}