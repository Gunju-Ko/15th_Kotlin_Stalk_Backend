package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Token extends AbstractEntity {

    private String token;

    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_token_member"))
    private Member member;
}
