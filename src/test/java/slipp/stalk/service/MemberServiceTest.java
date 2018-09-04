package slipp.stalk.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.TokenAlreadyRegisterException;
import slipp.stalk.controller.exceptions.TokenNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.infra.jpa.repository.MemberRepository;
import slipp.stalk.infra.jpa.repository.TokenRepository;
import slipp.stalk.support.DataJpaIntegrationTest;
import slipp.stalk.support.MemberTestMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MemberServiceTest extends DataJpaIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenRepository tokenRepository;
    private MemberService service;
    private long id = 1;

    @Before
    public void setUp() {
        service = new MemberService(memberRepository, tokenRepository);
    }

    @Test
    public void get__return_empty_optional_when_member_is_not_exist() {
        long notExistMemberId = id + 100;
        Optional<Member> member = service.get(notExistMemberId);
        assertThat(member.isPresent()).isFalse();
    }

    @Test
    public void get__return_member_when_member_is_exist() {
        Optional<Member> member = service.get(id);
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getMemberId()).isEqualTo("gunju");
    }

    @Test
    public void registerToken__should_register_token() {
        String token = "token";
        service.registerToken(id, token);

        assertThat(memberHasToken(find(Member.class, id), token)).isTrue();
    }

    @Test
    public void registerToken__should_throw_MemberNotFoundException_when_member_is_not_exist() throws Exception {
        long notExistMemberId = id + 100;
        String token = "token";
        Throwable t = catchThrowable(() -> service.registerToken(notExistMemberId, token));
        assertThat(t).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    public void registerToken__should_throw_TokenAlreadyExistException_when_token_is_already_exist() throws Exception {
        String token = "token";

        service.registerToken(id, token);
        Throwable t = catchThrowable(() -> service.registerToken(id, token));
        assertThat(t).isInstanceOf(TokenAlreadyRegisterException.class);
    }

    @Test
    public void deleteToken__should_throw_TokenNotFoundException_when_token_is_not_exist() throws Exception {
        String notExistToken = "token";

        Throwable t = catchThrowable(() -> service.deleteToken(id, notExistToken));
        assertThat(t).isInstanceOf(TokenNotFoundException.class);
    }

    @Test
    public void deleteToken__should_throw_TokenNotFoundException_when_member_doesnt_has_that_token() throws Exception {
        String token = "token";
        registerToken(id, token);

        long newId = saveTestData(MemberTestMother.memberBuilder()
                                                  .email("gunjuko@gmail.com")
                                                  .memberId("new member")
                                                  .name("gunju ko")
                                                  .password("test")
                                                  .build()).getId();

        Throwable t = catchThrowable(() -> service.deleteToken(newId, token));
        assertThat(t).isInstanceOf(TokenNotFoundException.class);
    }

    @Test
    public void deleteToken__should_throw_MemberNotFoundException_when_token_is_not_exist() throws Exception {
        String token = "token";
        long notExistMemberId = id + 100;

        Throwable t = catchThrowable(() -> service.deleteToken(notExistMemberId, token));
        assertThat(t).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    public void deleteToken__should_delete_token() throws Exception {
        String token = "token";
        registerToken(id, token);

        service.deleteToken(id, token);
        assertThat(memberHasToken(find(Member.class, id), token)).isFalse();
    }

    private void registerToken(long id, String token) {
        service.registerToken(id, token);
    }

    private boolean memberHasToken(Member m, String token) {
        return m.getTokens().stream()
                .anyMatch(k -> k.getValue().equals(token));
    }
}