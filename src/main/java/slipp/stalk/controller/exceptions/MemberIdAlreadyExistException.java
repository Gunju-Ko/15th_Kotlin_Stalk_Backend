package slipp.stalk.controller.exceptions;

import org.springframework.util.StringUtils;

public class MemberIdAlreadyExistException extends ConflictException {
    private final String memberId;

    public MemberIdAlreadyExistException(String memberId) {
        if (StringUtils.isEmpty(memberId)) {
            throw new IllegalArgumentException("memberId should not be empty");
        }
        this.memberId = memberId;
    }

    @Override
    public String errorMessage() {
        return String.format("%s는 이미 존재하는 회원 아이디입니다", memberId);
    }
}
