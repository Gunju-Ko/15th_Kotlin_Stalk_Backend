package slipp.stalk.commoon.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.UnAuthorizedException;
import slipp.stalk.domain.Member;
import slipp.stalk.infra.jpa.repository.MemberRepository;
import slipp.stalk.service.security.JwtHelper;
import slipp.stalk.service.security.JwtProperties;
import slipp.stalk.service.security.JwtToken;

import java.util.Optional;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProperties jwtProperties;
    private final JwtHelper jwtHelper;
    private final MemberRepository memberRepository;

    public LoginUserHandlerMethodArgumentResolver(JwtProperties jwtProperties,
                                                  JwtHelper jwtHelper,
                                                  MemberRepository memberRepository) {
        this.jwtProperties = jwtProperties;
        this.jwtHelper = jwtHelper;
        this.memberRepository = memberRepository;
    }

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
        return null;
    }

    private JwtToken getJwtTokenFromRequest(NativeWebRequest webRequest) {
        String token = webRequest.getHeader(jwtProperties.getHeader());
        if (token == null) {
            return null;
        }
        return new JwtToken(token);
    }

    private Member getMemberFromJwtToken(JwtToken token) {
        String email = jwtHelper.parseSubject(token);
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.orElseThrow(MemberNotFoundException::new);
    }
}
