package slipp.stalk.service.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

    private final JwtProperties properties;

    public JwtConfiguration(JwtProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper(properties);
    }
}