package slipp.stalk.infra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slipp.stalk.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}
