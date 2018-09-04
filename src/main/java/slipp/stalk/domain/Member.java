package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member extends AbstractEntity {

    @Column(name = "MEMBER_ID", unique = true, nullable = false)
    private String memberId;

    @Column(name = "MEMBER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "MEMBER_NAME", nullable = false)
    private String name;

    @Column(name = "MEMBER_EMAIL")
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

    public void addToken(String token) {
        this.tokens.add(new Token(token, this));
    }
}