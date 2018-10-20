package slipp.stalk.controller.exceptions;

public class AccessDeniedException extends ForbiddenException {
    @Override
    public String errorMessage() {
        return "해당 자원에 접근 권한이 없습니다";
    }
}
