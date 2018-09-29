package slipp.stalk.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.CreateMessageDto;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.controller.dto.MessagesDto;
import slipp.stalk.controller.exceptions.UpdateMessageDto;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.Message;
import slipp.stalk.service.message.MessageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members/messages")
public class MessageController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<MessagesDto> getMessages(@LoginUser Member member) {
        List<MessageDto> messages = messageService.getMessages(member.getId())
                                                  .stream()
                                                  .map(this::mapToMessageDto)
                                                  .collect(Collectors.toList());
        return ResponseEntity.ok(new MessagesDto(messages));
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@LoginUser Member member,
                                                    @RequestBody @Valid CreateMessageDto createMessageDto) {
        Message message = messageService.createMessage(member.getId(), createMessageDto.getMessage());
        return ResponseEntity.ok(mapToMessageDto(message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(@LoginUser Member member,
                                                    @PathVariable long id,
                                                    @RequestBody @Valid UpdateMessageDto updateMessageDto) {
        Message message = messageService.updateMessage(member.getId(), id, updateMessageDto.getUpdateMessage());
        return ResponseEntity.ok(mapToMessageDto(message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@LoginUser Member member,
                                              @PathVariable long id) {
        messageService.delete(member.getId(), id);
        return ResponseEntity.noContent()
                             .build();
    }

    private MessageDto mapToMessageDto(Message m) {
        return modelMapper.map(m, MessageDto.class);
    }
}
