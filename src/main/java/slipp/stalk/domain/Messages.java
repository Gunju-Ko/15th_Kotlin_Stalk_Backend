package slipp.stalk.domain;

import slipp.stalk.controller.exceptions.MessageAlreadyExistException;
import slipp.stalk.controller.exceptions.MessageNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Embeddable
class Messages {

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    List<Message> getMessages() {
        return messages;
    }

    Optional<Message> getMessage(long messageId) {
        return findMessage(m -> m.getId() == messageId);
    }

    Message addMessage(String message, Member member) {
        findMessage(m -> m.getMessage().equals(message))
            .ifPresent(m -> {
                throw new MessageAlreadyExistException(m.getMessage());
            });
        Message m = new Message(message, member);
        messages.add(m);
        return m;
    }

    void deleteMessage(long messageId) {
        Message message = findMessage(m -> m.getId() == messageId)
            .orElseThrow(MessageNotFoundException::new);
        message.setMember(null);
        this.messages.remove(message);
    }

    Message updateMessage(long messageId, String updateMessage) {
        Message message = findMessage(m -> m.getId() == messageId)
            .orElseThrow(MessageNotFoundException::new);
        message.setMessage(updateMessage);
        return message;
    }

    private Optional<Message> findMessage(Predicate<Message> predicate) {
        return messages.stream()
                       .filter(predicate)
                       .findFirst();
    }
}
