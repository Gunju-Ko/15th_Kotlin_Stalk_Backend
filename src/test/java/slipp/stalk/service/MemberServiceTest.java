package slipp.stalk.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import slipp.stalk.domain.Member;
import slipp.stalk.repository.MemberRepository;
import slipp.stalk.support.DataJpaIntegrationTest;
import slipp.stalk.support.MemberTestMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest extends DataJpaIntegrationTest {

    @Autowired
    private MemberRepository repository;
    private MemberService service;
    private long id;

    @Before
    public void setUp() {
        service = new MemberService(repository);

        Member member = MemberTestMother.memberBuilder()
                                        .name("Ko Gunju")
                                        .email("gunju@slipp.com")
                                        .memberId("gunju")
                                        .password("test")
                                        .build();
        id = saveTestData(member).getId();
    }

    @Test
    public void return_empty_optional_when_member_is_not_exist() {
        long notExistMemberId = id + 100;
        Optional<Member> member = service.get(notExistMemberId);
        assertThat(member.isPresent()).isFalse();
    }

    @Test
    public void return_member_when_member_is_exist() {
        Optional<Member> member = service.get(id);
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getMemberId()).isEqualTo("gunju");
    }
}