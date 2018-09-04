package slipp.stalk.controller.exceptions;

public class TokenNotFoundException extends NotFoundException {

    private static final String MESSAGE = "%s 토큰은 존재하지 않습니다";
    private final String token;

    public TokenNotFoundException(String token) {
        this.token = token;
    }

    @Override
    public String errorMessage() {
        return String.format(MESSAGE, token);
    }
}
