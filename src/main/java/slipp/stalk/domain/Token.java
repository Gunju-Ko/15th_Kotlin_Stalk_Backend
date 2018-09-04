package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Token extends AbstractEntity {

    @Column(unique = true)
    private String value;
    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_token_member"))
    private Member member;

    public Token() {
    }

    Token(String value, Member member) {
        this.value = value;
        this.member = member;
    }
}
