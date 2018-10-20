package slipp.stalk.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.domain.Member;

@RestController
public class LoginUserTestController {

    private final ModelMapper modelMapper;

    public LoginUserTestController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/loginrequired")
    public ResponseEntity<MemberDto> userLoginRequired(@LoginUser Member member) {
        return ResponseEntity.ok(mapToMemberDto(member));
    }

    @GetMapping("/users/notrequired")
    public ResponseEntity<MemberDto> userLoginNotRequired(@LoginUser(required = false) Member member) {
        return ResponseEntity.ok(mapToMemberDto(member));
    }

    @GetMapping("/admin/loginrequired")
    public ResponseEntity<MemberDto> adminLoginRequired(@LoginUser(role = Member.Role.ADMIN) Member adminMember) {
        return ResponseEntity.ok(mapToMemberDto(adminMember));
    }

    private MemberDto mapToMemberDto(Member member) {
        if (member == null) {
            return null;
        }
        return modelMapper.map(member, MemberDto.class);
    }

}