package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Setter
@Getter
@Entity
public class Message extends AbstractEntity {

    @Column(name = "message", nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_message_member"))
    private Member member;

    public Message(String message, Member member) {
        this.message = message;
        this.member = member;
    }

    public Message() {
    }
}
