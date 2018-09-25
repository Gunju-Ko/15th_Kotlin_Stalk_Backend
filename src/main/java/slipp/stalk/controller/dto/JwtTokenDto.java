package slipp.stalk.controller.dto;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String token;

    public JwtTokenDto() {
    }

    public JwtTokenDto(String token) {
        this.token = token;
    }
}
