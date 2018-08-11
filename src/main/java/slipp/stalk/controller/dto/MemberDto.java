package slipp.stalk.controller.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String email;
}
