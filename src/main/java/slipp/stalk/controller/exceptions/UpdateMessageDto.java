package slipp.stalk.controller.exceptions;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMessageDto {
    @NotEmpty
    private String updateMessage;

    public UpdateMessageDto(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public UpdateMessageDto() {
    }
}
