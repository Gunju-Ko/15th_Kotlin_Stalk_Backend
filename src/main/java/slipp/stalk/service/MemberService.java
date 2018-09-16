package slipp.stalk.service;

import org.springframework.stereotype.Service;
import slipp.stalk.controller.exceptions.MemberIdAlreadyExistException;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.TokenAlreadyRegisterException;
import slipp.stalk.controller.exceptions.TokenNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.Token;
import slipp.stalk.infra.jpa.repository.MemberRepository;
import slipp.stalk.infra.jpa.repository.TokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    public MemberService(MemberRepository memberRepository,
                         TokenRepository tokenRepository) {
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
    }

    public Optional<Member> get(long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Member create(Member body) {
        memberRepository.findByMemberId(body.getMemberId())
                        .ifPresent(t -> {
                            throw new MemberIdAlreadyExistException(t.getMemberId());
                        });
        return memberRepository.save(body);
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
        Token t = tokenRepository.findByValue(token)
                                 .orElseThrow(() -> new TokenNotFoundException(token));
        if (!m.deleteToken(t)) {
            throw new TokenNotFoundException(token);
        }
    }
}
