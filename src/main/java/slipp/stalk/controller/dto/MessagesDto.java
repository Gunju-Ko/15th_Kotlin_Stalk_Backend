package slipp.stalk.controller.dto;

import java.util.List;
import java.util.Objects;

public class MessagesDto {

    private List<MessageDto> messages;

    public MessagesDto() {
    }

    public MessagesDto(List<MessageDto> messages) {
        this.messages = Objects.requireNonNull(messages);
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public int size() {
        return messages.size();
    }
}
