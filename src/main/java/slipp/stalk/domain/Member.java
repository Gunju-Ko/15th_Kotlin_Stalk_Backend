package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Optional;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Tokens tokens = new Tokens();
    @Embedded
    private Messages messages = new Messages();

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public enum Role {
        ADMIN,
        USER
    }

    public List<Token> getTokens() {
        return tokens.getTokens();
    }

    public void addToken(String token) {
        this.tokens.addToken(token, this);
    }

    public boolean deleteToken(String token) {
        return tokens.deleteToken(token);
    }

    public List<Message> getMessages() {
        return messages.getMessages();
    }

    public Optional<Message> getMessage(long messageId) {
        return messages.getMessage(messageId);
    }

    public Message addMessage(String message) {
        return messages.addMessage(message, this);
    }

    public void deleteMessage(long messageId) {
        messages.deleteMessage(messageId);
    }

    public Message updateMessage(long messageId, String updateMessage) {
        return messages.updateMessage(messageId, updateMessage);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}