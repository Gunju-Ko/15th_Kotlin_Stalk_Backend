package slipp.stalk.service;

import org.springframework.stereotype.Service;
import slipp.stalk.domain.Member;
import slipp.stalk.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Optional<Member> get(long id) {
        return repository.findById(id);
    }
}
