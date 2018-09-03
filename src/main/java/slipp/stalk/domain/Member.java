package slipp.stalk.domain;

import lombok.Getter;
import lombok.Setter;
import slipp.stalk.domain.support.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "member")
    private List<Token> tokens;
}