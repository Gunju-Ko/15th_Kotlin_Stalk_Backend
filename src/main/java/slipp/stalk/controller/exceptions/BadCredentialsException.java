package slipp.stalk.controller.exceptions;

public class BadCredentialsException extends ForbiddenException {
    @Override
    public String errorMessage() {
        return "잘못된 비밀번호 입니다. 비밀번호를 확인하세요";
    }
}
