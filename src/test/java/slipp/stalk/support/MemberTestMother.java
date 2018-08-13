package slipp.stalk.support;

import slipp.stalk.domain.Member;

public class MemberTestMother {

    private MemberTestMother() {
    }

    public static MemberBuilder memberBuilder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {
        private String memberId;
        private String password;
        private String name;
        private String email;

        private MemberBuilder() {
        }

        public MemberBuilder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Member build() {
            Member member = new Member();
            member.setMemberId(memberId);
            member.setPassword(password);
            member.setName(name);
            member.setEmail(email);

            return member;
        }
    }
}
