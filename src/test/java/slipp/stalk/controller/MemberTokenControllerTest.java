package slipp.stalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import slipp.stalk.controller.dto.FireabseTokenDto;
import slipp.stalk.controller.exceptions.TokenNotFoundException;
import slipp.stalk.service.member.MemberService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberTokenControllerTest {

    private MockMvc mvc;
    private MemberService memberService;

    @Before
    public void setUp() throws Exception {
        memberService = Mockito.mock(MemberService.class);
        mvc = MockMvcBuilders.standaloneSetup(new MemberTokenController(memberService))
                             .setControllerAdvice(new RestApiControllerAdvice())
                             .build();
    }

    @Test
    public void should_return_204_when_token_is_deleted() throws Exception {
        String token = "test";
        long memberId = 0L;

        mvc.perform(delete("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(204));

        verify(memberService).deleteToken(memberId, token);
    }

    @Test
    public void should_return_404_when_token_is_not_found() throws Exception {
        String token = "test";
        long memberId = 0L;

        doThrow(TokenNotFoundException.class)
            .when(memberService).deleteToken(memberId, token);

        mvc.perform(delete("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(404));

        verify(memberService).deleteToken(memberId, token);
    }

    private String body(String token) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(FireabseTokenDto.of(token));
    }
}