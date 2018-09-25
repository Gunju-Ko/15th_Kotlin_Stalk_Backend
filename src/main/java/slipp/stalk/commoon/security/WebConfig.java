package slipp.stalk.commoon.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    public WebConfig(LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver) {
        this.loginUserHandlerMethodArgumentResolver = loginUserHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(0, loginUserHandlerMethodArgumentResolver);
    }
}
