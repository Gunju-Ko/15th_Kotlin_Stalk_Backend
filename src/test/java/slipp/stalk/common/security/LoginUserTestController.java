package slipp.stalk.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.domain.Member;

@RestController
public class LoginUserTestController {

    private final ModelMapper modelMapper;

    public LoginUserTestController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/loginrequired")
    public ResponseDto<MemberDto> userLoginRequired(@LoginUser Member member) {
        return ResponseDto.ok(mapToMemberDto(member));
    }

    @GetMapping("/users/notrequired")
    public ResponseDto<MemberDto> userLoginNotRequired(@LoginUser(required = false) Member member) {
        return ResponseDto.ok(mapToMemberDto(member));
    }

    @GetMapping("/admin/loginrequired")
    public ResponseDto<MemberDto> adminLoginRequired(@LoginUser(role = Member.Role.ADMIN) Member adminMember) {
        return ResponseDto.ok(mapToMemberDto(adminMember));
    }

    private MemberDto mapToMemberDto(Member member) {
        if (member == null) {
            return null;
        }
        return modelMapper.map(member, MemberDto.class);
    }

}