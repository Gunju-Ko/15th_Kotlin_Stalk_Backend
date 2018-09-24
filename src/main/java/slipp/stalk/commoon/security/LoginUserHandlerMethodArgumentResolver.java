package slipp.stalk.commoon.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import slipp.stalk.controller.exceptions.UnAuthorizedException;
import slipp.stalk.domain.Member;
import slipp.stalk.service.security.JwtToken;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        JwtToken token = getJwtTokenFromRequest(webRequest);
        if (token != null) {
            return getMemberFromJwtToken(token);
        }
        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        if (loginUser != null && loginUser.required()) {
            throw new UnAuthorizedException("You're required Login!");
        }
        return defaultUser();
    }

    private JwtToken getJwtTokenFromRequest(NativeWebRequest webRequest) {
        return null;
    }

    private Member getMemberFromJwtToken(JwtToken token) {
        return null;
    }

    private Member defaultUser() {
        return null;
    }
}
