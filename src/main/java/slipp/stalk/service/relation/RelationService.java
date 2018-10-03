package slipp.stalk.service.relation;

import org.springframework.stereotype.Service;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.RelationShip;
import slipp.stalk.infra.jpa.repository.RelationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationService {

    private final RelationRepository relationRepository;

    public RelationService(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    public List<Member> getFriends(Member loginUser) {
        return relationRepository.findByFromAndRelationShipType(loginUser, RelationShip.RelationShipType.FRIENDSHIP)
                                 .stream()
                                 .map(r -> r.getId().getTo())
                                 .collect(Collectors.toList());
    }
}
