package slipp.stalk.service.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String privateKey;
    private String header;
    private int expirationHour;
}
