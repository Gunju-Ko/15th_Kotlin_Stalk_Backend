package slipp.stalk.infra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slipp.stalk.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
}
