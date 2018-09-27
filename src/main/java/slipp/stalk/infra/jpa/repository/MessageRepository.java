package slipp.stalk.infra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slipp.stalk.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
