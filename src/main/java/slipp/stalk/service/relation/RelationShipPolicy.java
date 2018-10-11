package slipp.stalk.service.relation;

import org.springframework.stereotype.Service;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.RelationShipKey;
import slipp.stalk.infra.jpa.repository.RelationRepository;

@Service
public class RelationShipPolicy {

    private final RelationRepository relationRepository;

    public RelationShipPolicy(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    public boolean canBecomeFriend(Member loginUser, Member newFriend) {
        RelationShipKey key = createKey(loginUser, newFriend);
        return !relationRepository.findById(key)
                                  .isPresent();
    }

    private RelationShipKey createKey(Member from, Member to) {
        return new RelationShipKey(from, to);
    }
}
