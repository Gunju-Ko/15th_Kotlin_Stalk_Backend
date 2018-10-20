package slipp.stalk.controller.dto;

import lombok.Data;

@Data
public class LogoutDto {
    private String token;

    public LogoutDto() {
    }

    public LogoutDto(String token) {
        this.token = token;
    }
}
