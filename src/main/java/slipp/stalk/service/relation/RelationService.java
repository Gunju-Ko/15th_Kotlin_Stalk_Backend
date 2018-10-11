package slipp.stalk.service.relation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slipp.stalk.controller.exceptions.CannotBeFriendException;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.RelationNotFoundException;
import slipp.stalk.controller.exceptions.UnexpectedRelationException;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.RelationShip;
import slipp.stalk.domain.RelationShipKey;
import slipp.stalk.infra.jpa.repository.RelationRepository;
import slipp.stalk.service.member.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationService {

    private final RelationRepository relationRepository;
    private final RelationShipPolicy relationShipPolicy;
    private final MemberService memberService;

    public RelationService(RelationRepository relationRepository,
                           RelationShipPolicy relationShipPolicy,
                           MemberService memberService) {
        this.relationRepository = relationRepository;
        this.memberService = memberService;
        this.relationShipPolicy = relationShipPolicy;
    }

    public List<Member> getFriends(Member loginUser) {
        return relationRepository.findByFromAndRelationShipType(loginUser, RelationShip.RelationShipType.FRIENDSHIP)
                                 .stream()
                                 .map(r -> r.getId().getTo())
                                 .collect(Collectors.toList());
    }

    @Transactional
    public void addFriend(Member loginUser, long memberId) {
        Member newFriend = memberService.get(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        if (!relationShipPolicy.canBecomeFriend(loginUser, newFriend)) {
            throw new CannotBeFriendException(loginUser, newFriend);
        }
        relationRepository.save(RelationShip.createFriendShip(loginUser, newFriend));
    }

    public void deleteFriend(Member loginUser, long memberId) {
        Member friend = memberService.get(memberId)
                                     .orElseThrow(MemberNotFoundException::new);
        RelationShipKey key = createKey(loginUser, friend);
        RelationShip relationShip = relationRepository.findById(key)
                                                      .orElseThrow(() -> new RelationNotFoundException(loginUser, friend));
        if (!relationShip.isFriendShip()) {
            throw new UnexpectedRelationException("친구 관계가 아닙니다");
        }
        relationRepository.delete(relationShip);
    }

    private RelationShipKey createKey(Member from, Member to) {
        return new RelationShipKey(from, to);
    }
}
