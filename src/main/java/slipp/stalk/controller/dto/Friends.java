package slipp.stalk.controller.dto;

import slipp.stalk.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public class Friends {

    private List<Friend> friends;

    private Friends() {
    }

    private Friends(List<Friend> friends) {
        this.friends = friends;
    }

    public static Friends of(List<Member> friends) {
        return new Friends(friends.stream()
                                  .map(Friend::of)
                                  .collect(Collectors.toList()));
    }

    public List<Friend> getFriends() {
        return friends;
    }

    private static class Friend {
        private long id;
        private String name;
        private String email;

        private Friend() {
        }

        private Friend(long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public static Friend of(Member member) {
            return new Friend(member.getId(), member.getName(), member.getEmail());
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
