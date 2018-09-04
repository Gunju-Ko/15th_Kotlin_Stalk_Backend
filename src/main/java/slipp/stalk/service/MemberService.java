package slipp.stalk.service;

import org.springframework.stereotype.Service;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.TokenAlreadyRegisterException;
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
    public void registerToken(long id, String token) {
        Member m = get(id).orElseThrow(MemberNotFoundException::new);
        Token t = tokenRepository.findByValue(token);
        if (t != null) {
            throw new TokenAlreadyRegisterException(token);
        }
        m.addToken(token);
    }
}
