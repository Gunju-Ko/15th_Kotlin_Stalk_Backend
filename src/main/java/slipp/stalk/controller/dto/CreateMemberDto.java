package slipp.stalk.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
public class CreateMemberDto {
    @Length(min = 4, max = 15)
    private String memberId;
    @Length(min = 6, max = 15)
    private String password;
    @Length(min = 2, max = 15)
    private String name;
    @Email
    private String email;
}
