package slipp.stalk.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
public class LoginInfoDto {
    @Email
    private String email;
    @Length(min = 6, max = 15)
    private String password;
}
