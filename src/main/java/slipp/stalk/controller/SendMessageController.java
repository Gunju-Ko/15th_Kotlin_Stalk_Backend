package slipp.stalk.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.domain.Member;
import slipp.stalk.service.message.MessageService;

@RestController
@RequestMapping("/members/{memberId}")
public class SendMessageController {

    private final MessageService messageService;

    public SendMessageController(MessageService memberService) {
        this.messageService = memberService;
    }

    @PostMapping("/messages/{messageId}")
    public ResponseDto<Void> sendMessage(@LoginUser Member from,
                                         @PathVariable long memberId,
                                         @PathVariable long messageId) {
        messageService.sendMessages(from, memberId, messageId);

        return ResponseDto.noContent();
    }

}
