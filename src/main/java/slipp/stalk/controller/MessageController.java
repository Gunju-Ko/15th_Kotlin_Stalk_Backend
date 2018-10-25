package slipp.stalk.controller;

import org.modelmapper.ModelMapper;
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
import slipp.stalk.controller.dto.ResponseDto;
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
    public ResponseDto<MessagesDto> getMessages(@LoginUser Member member) {
        List<MessageDto> messages = messageService.getMessages(member.getId())
                                                  .stream()
                                                  .map(this::mapToMessageDto)
                                                  .collect(Collectors.toList());
        return ResponseDto.ok(new MessagesDto(messages));
    }

    @PostMapping
    public ResponseDto<MessageDto> createMessage(@LoginUser Member member,
                                                 @RequestBody @Valid CreateMessageDto createMessageDto) {
        Message message = messageService.createMessage(member.getId(), createMessageDto.getMessage());
        return ResponseDto.ok(mapToMessageDto(message));
    }

    @PutMapping("/{id}")
    public ResponseDto<MessageDto> updateMessage(@LoginUser Member member,
                                                 @PathVariable long id,
                                                 @RequestBody @Valid UpdateMessageDto updateMessageDto) {
        Message message = messageService.updateMessage(member.getId(), id, updateMessageDto.getUpdateMessage());
        return ResponseDto.ok(mapToMessageDto(message));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteMessage(@LoginUser Member member,
                                           @PathVariable long id) {
        messageService.delete(member.getId(), id);
        return ResponseDto.noContent();
    }

    private MessageDto mapToMessageDto(Message m) {
        return modelMapper.map(m, MessageDto.class);
    }
}
