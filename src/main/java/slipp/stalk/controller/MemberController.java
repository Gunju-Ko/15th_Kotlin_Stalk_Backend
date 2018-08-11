package slipp.stalk.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.controller.dto.MemberDto;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.service.MemberService;

@RestController
@RequestMapping("/members/")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberDto> get(@PathVariable long id) {
        Member member = memberService.get(id)
                                     .orElseThrow(MemberNotFoundException::new);
        return ResponseEntity.ok(mapToMemberDto(member));
    }

    private MemberDto mapToMemberDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
}
