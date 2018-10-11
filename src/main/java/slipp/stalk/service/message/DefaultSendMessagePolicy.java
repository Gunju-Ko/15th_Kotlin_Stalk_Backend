package slipp.stalk.service.message;

import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.RelationShip;
import slipp.stalk.domain.RelationShipKey;
import slipp.stalk.infra.jpa.repository.RelationRepository;

@Component
public class DefaultSendMessagePolicy implements SendMessagePolicy {

    private final RelationRepository relationRepository;

    public DefaultSendMessagePolicy(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Override
    public boolean canSendMessage(Member from, Member to) {
        RelationShipKey key = new RelationShipKey(from, to);
        return relationRepository.findById(key)
                                 .map(RelationShip::isFriendShip)
                                 .orElse(false);
    }
}
