package slipp.stalk.controller.exceptions;

import org.springframework.util.StringUtils;

public class MemberEmailAlreadyExistException extends ConflictException {
    private final String memberId;

    public MemberEmailAlreadyExistException(String memberId) {
        if (StringUtils.isEmpty(memberId)) {
            throw new IllegalArgumentException("memberId should not be empty");
        }
        this.memberId = memberId;
    }

    @Override
    public String errorMessage() {
        return String.format("%s는 이미 존재하는 회원 이메일입니다", memberId);
    }
}
