package slipp.stalk.controller.exceptions;

import org.springframework.util.StringUtils;

public class TokenAlreadyRegisterException extends ConflictException {

    private static final String MESSAGE = "%s 토큰은 이미 등록되었습니다";

    private final String token;

    public TokenAlreadyRegisterException(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token should not be empty");
        }
        this.token = token;
    }

    @Override
    public String errorMessage() {
        return String.format(MESSAGE, token);
    }
}
