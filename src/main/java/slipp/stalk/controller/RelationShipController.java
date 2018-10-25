package slipp.stalk.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.commoon.security.LoginUser;
import slipp.stalk.controller.dto.Friends;
import slipp.stalk.controller.dto.ResponseDto;
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
    public ResponseDto<Friends> getFriends(@LoginUser Member loginUser) {
        List<Member> friends = relationService.getFriends(loginUser);
        return ResponseDto.ok(Friends.of(friends));
    }

    @PostMapping("/friends/{memberId}")
    public ResponseDto<Void> addFriend(@LoginUser Member loginUser, @PathVariable long memberId) {
        relationService.addFriend(loginUser, memberId);
        return ResponseDto.noContent();
    }

    @DeleteMapping("/friends/{memberId}")
    public ResponseDto<Void> deleteFriend(@LoginUser Member loginUser, @PathVariable long memberId) {
        relationService.deleteFriend(loginUser, memberId);
        return ResponseDto.noContent();
    }
}