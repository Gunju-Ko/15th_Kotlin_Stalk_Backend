package slipp.stalk.controller.exceptions;

import org.apache.commons.lang3.StringUtils;

public class UnexpectedRelationException extends BadRequestException {

    private final String message;

    public UnexpectedRelationException(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException();
        }
        this.message = message;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
