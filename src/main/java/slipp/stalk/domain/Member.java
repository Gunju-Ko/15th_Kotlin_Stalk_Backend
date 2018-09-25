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

    @Column(name = "MEMBER_NAME", nullable = false)
    private String name;
    @Column(name = "MEMBER_EMAIL")
    private String email;
    @Column(name = "MEMBER_PASSWORD", nullable = false)
    private String password;
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

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
}