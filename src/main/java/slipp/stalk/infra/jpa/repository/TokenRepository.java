package slipp.stalk.infra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slipp.stalk.domain.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);
}
