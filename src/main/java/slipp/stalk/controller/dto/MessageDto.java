package slipp.stalk.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MessageDto {

    @NotEmpty
    private String message;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
    }
}
