package slipp.stalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slipp.stalk.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
