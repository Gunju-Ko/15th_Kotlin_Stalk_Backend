package slipp.stalk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.service.MemberService;
import slipp.stalk.service.messaging.SendMessageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members/{id}")
public class SendMessageController {

    private final SendMessageService sendMessageService;
    private final MemberService memberService;

    public SendMessageController(SendMessageService sendMessageService,
                                 MemberService memberService) {
        this.sendMessageService = sendMessageService;
        this.memberService = memberService;
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@PathVariable long id,
                                            @RequestBody @Valid MessageDto message) {
        Member member = memberService.get(id)
                                     .orElseThrow(MemberNotFoundException::new);
        sendMessageService.sendMessages(null, member, message.getMessage());

        return ResponseEntity.noContent()
                             .build();
    }

}
