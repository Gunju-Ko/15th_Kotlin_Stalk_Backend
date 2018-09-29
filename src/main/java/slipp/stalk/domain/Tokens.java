package slipp.stalk.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
class Tokens {

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

    void addToken(String token, Member member) {
        this.tokens.add(new Token(token, member));
    }

    boolean deleteToken(Token token) {
        token.setMember(null);
        return tokens.remove(token);
    }

    List<Token> getTokens() {
        return this.tokens;
    }
}
