package slipp.stalk.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FireabseTokenDto {

    @NotEmpty
    private String token;

    public FireabseTokenDto() {
    }

    public FireabseTokenDto(String token) {
        this.token = token;
    }
}
