package slipp.stalk.service.member;

import org.springframework.stereotype.Service;
import slipp.stalk.controller.exceptions.BadCredentialsException;
import slipp.stalk.controller.exceptions.MemberEmailAlreadyExistException;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.TokenAlreadyRegisterException;
import slipp.stalk.controller.exceptions.TokenNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.infra.jpa.repository.MemberRepository;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final List<CreateMemberListener> createMemberListeners;

    public MemberService(MemberRepository memberRepository,
                         TokenRepository tokenRepository,
                         List<CreateMemberListener> createMemberListeners) {
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
        this.createMemberListeners = createMemberListeners;
    }

    public Optional<Member> get(long id) {
        return memberRepository.findById(id);
    }

    public Member getLoginMember(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                                        .orElseThrow(MemberNotFoundException::new);
        if (!member.checkPassword(password)) {
            throw new BadCredentialsException();
        }
        return member;
    }

    @Transactional
    public Member create(Member body) {
        memberRepository.findByEmail(body.getEmail())
                        .ifPresent(t -> {
                            throw new MemberEmailAlreadyExistException(t.getEmail());
                        });
        Member member = memberRepository.save(body);
        createMemberListeners.forEach(listener -> listener.created(member));
        return member;
    }

    public void delete(long id) {
        get(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(id);
    }

    @Transactional
    public void registerToken(long id, String token) {
        Member m = get(id).orElseThrow(MemberNotFoundException::new);
        tokenRepository.findByValue(token)
                       .ifPresent(t -> {
                           throw new TokenAlreadyRegisterException(t.getValue());
                       });

        m.addToken(token);
    }

    @Transactional
    public void deleteToken(long id, String token) {
        Member m = get(id).orElseThrow(MemberNotFoundException::new);
        if (!m.deleteToken(token)) {
            throw new TokenNotFoundException(token);
        }
    }
}
