package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.controller.exceptions.MessageAlreadyExistException;
import slipp.stalk.controller.exceptions.MessageNotFoundException;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@Setter
@Entity
public class Member extends AbstractEntity {

    @Column(name = "MEMBER_NAME", nullable = false)
    private String name;
    @Column(name = "MEMBER_EMAIL")
    private String email;
    @Column(name = "MEMBER_PASSWORD", nullable = false)
    private String password;
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public void addToken(String token) {
        this.tokens.add(new Token(token, this));
    }

    public boolean deleteToken(Token token) {
        token.setMember(null);
        return tokens.remove(token);
    }

    public boolean friendWith(Member from) {
        // TODO : Implementation
        return false;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Optional<Message> getMessage(long messageId) {
        return findMessage(m -> m.getId() == messageId);
    }

    public Message addMessage(String message) {
        findMessage(m -> m.getMessage().equals(message)).ifPresent(m -> {
            throw new MessageAlreadyExistException(m.getMessage());
        });
        Message m = new Message(message, this);
        messages.add(m);
        return m;
    }

    public void deleteMessage(long messageId) {
        Message message = findMessage(m -> m.getId() == messageId).orElseThrow(MessageNotFoundException::new);
        message.setMember(null);
        this.messages.remove(message);
    }

    public Message updateMessage(long messageId, String updateMessage) {
        Message message = findMessage(m -> m.getId() == messageId).orElseThrow(MessageNotFoundException::new);
        message.setMessage(updateMessage);
        return message;
    }

    private Optional<Message> findMessage(Predicate<Message> predicate) {
        return messages.stream()
                       .filter(predicate)
                       .findFirst();
    }
}