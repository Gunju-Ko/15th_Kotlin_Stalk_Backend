package slipp.stalk.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.CreateMemberDto;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.service.MemberService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<MemberDto> get(@LoginUser Member loginUser) {
        Member member = memberService.get(loginUser.getId())
                                     .orElseThrow(MemberNotFoundException::new);
        return ResponseEntity.ok(mapToMemberDto(member));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginUser Member loginUser) {
        memberService.delete(loginUser.getId());
        return ResponseEntity.noContent()
                             .build();
    }

    @PostMapping
    public ResponseEntity<MemberDto> create(@RequestBody @Valid CreateMemberDto body) {
        MemberDto member = mapToMemberDto(memberService.create(mapToMember(body)));
        return new ResponseEntity<>(member, member.makeHttpHeaders(), HttpStatus.OK);
    }

    private Member mapToMember(CreateMemberDto body) {
        return modelMapper.map(body, Member.class);
    }

    private MemberDto mapToMemberDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
}
