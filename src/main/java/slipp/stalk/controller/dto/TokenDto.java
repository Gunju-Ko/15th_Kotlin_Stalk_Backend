package slipp.stalk.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TokenDto {

    @NotEmpty
    private String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }
}
