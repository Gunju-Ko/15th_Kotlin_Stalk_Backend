package slipp.stalk.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import slipp.stalk.domain.Member;
import slipp.stalk.service.MemberService;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest {

    private MockMvc mockMvc;
    private MemberService memberService;

    private long existMemberId = 1L;

    @Before
    public void setUp() throws Exception {
        memberService = Mockito.mock(MemberService.class);
        doReturn(mockMember())
            .when(memberService).get(existMemberId);

        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService, new ModelMapper()))
                                 .setControllerAdvice(new RestApiControllerAdvice())
                                 .build();
    }

    @Test
    public void should_return_200_when_member_is_exist() throws Exception {
        mockMvc.perform(get("/members/" + existMemberId))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    public void should_return_404_when_member_is_not_exist() throws Exception {
        long notExistMemberId = 2L;
        mockMvc.perform(get("/members/" + notExistMemberId))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    private Optional<Member> mockMember() {
        Member member = new Member();
        member.setMemberId("test");
        member.setPassword("password");
        member.setName("gunju");
        member.setEmail("gunju@slipp.com");
        return Optional.of(member);
    }
}