package slipp.stalk.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MessageDto {
    private long id;
    @NotEmpty
    private String message;
}
