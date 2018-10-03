package slipp.stalk.domain;

import org.springframework.util.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
class Tokens {

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

    void addToken(String token, Member member) {
        this.tokens.add(new Token(token, member));
    }

    boolean deleteToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        return tokens.removeIf(t -> t.getValue().equals(token));
    }

    List<Token> getTokens() {
        return Collections.unmodifiableList(this.tokens);
    }
}
