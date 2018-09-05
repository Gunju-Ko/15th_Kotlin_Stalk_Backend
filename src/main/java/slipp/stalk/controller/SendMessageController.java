package slipp.stalk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.service.messaging.SendMessageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members/{id}")
public class SendMessageController {

    private final SendMessageService sendMessageService;

    public SendMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@PathVariable long id,
                                            @RequestBody @Valid MessageDto message) {
        sendMessageService.sendMessage(id, message.getMessage());

        return ResponseEntity.noContent()
                             .build();
    }

}
