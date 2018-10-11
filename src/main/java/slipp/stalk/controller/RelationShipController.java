package slipp.stalk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.Friends;
import slipp.stalk.domain.Member;
import slipp.stalk.service.relation.RelationService;

import java.util.List;

@RestController
@RequestMapping("/members/relations")
public class RelationShipController {

    private final RelationService relationService;

    public RelationShipController(RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping("/friends")
    public ResponseEntity<Friends> getFriends(@LoginUser Member loginUser) {
        List<Member> friends = relationService.getFriends(loginUser);
        return ResponseEntity.ok(Friends.of(friends));
    }

    @PostMapping("/friends/{memberId}")
    public ResponseEntity<Void> addFriend(@LoginUser Member loginUser, @PathVariable long memberId) {
        relationService.addFriend(loginUser, memberId);
        return ResponseEntity.noContent()
                             .build();
    }

    @DeleteMapping("/friends/{memberId}")
    public ResponseEntity<Void> deleteFriend(@LoginUser Member loginUser, @PathVariable long memberId) {
        relationService.deleteFriend(loginUser, memberId);
        return ResponseEntity.noContent()
                             .build();
    }
}