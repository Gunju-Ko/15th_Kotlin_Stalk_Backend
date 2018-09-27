package slipp.stalk.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMessageDto {
    @NotEmpty
    private String message;
}
