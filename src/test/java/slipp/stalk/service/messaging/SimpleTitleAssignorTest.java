package slipp.stalk.service.messaging;

import org.junit.Before;
import org.junit.Test;
import slipp.stalk.domain.Member;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SimpleTitleAssignorTest {

    private SimpleTitleAssignor assignor;

    @Before
    public void setUp() throws Exception {
        assignor = new SimpleTitleAssignor();
    }

    @Test
    public void should_return_unknown_when_from_is_null() {
        String title = assignor.makeTitle(null, null);
        assertThat(title).isEqualTo("unknown");
    }

    @Test
    public void should_return_MemberName_when_from_is_notNull() {
        String name = "Gunju";
        Member member = new Member();
        member.setName(name);

        String title = assignor.makeTitle(member, null);

        assertThat(title).isEqualTo(name);
    }
}