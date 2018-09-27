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
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.TokenAlreadyRegisterException;
import slipp.stalk.controller.exceptions.TokenNotFoundException;
import slipp.stalk.service.MemberService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void should_return_204_when_register_token_is_succeed() throws Exception {
        String token = "test token";
        long memberId = 0;

        mvc.perform(post("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(204));

        verify(memberService).registerToken(memberId, token);
    }

    @Test
    public void should_return_400_when_token_is_empty_string() throws Exception {
        String token = "";
        long memberId = 0;

        mvc.perform(post("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(400));

        verify(memberService, never()).registerToken(memberId, token);
    }

    @Test
    public void should_return_404_when_member_is_not_found() throws Exception {
        String token = "test token";
        long memberId = 0;

        doThrow(MemberNotFoundException.class)
            .when(memberService).registerToken(memberId, token);

        mvc.perform(post("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(404));

        verify(memberService).registerToken(memberId, token);
    }

    @Test
    public void should_return_409_when_token_is_already_registered() throws Exception {
        String token = "test token";
        long memberId = 0;

        doThrow(TokenAlreadyRegisterException.class)
            .when(memberService).registerToken(memberId, token);

        mvc.perform(post("/members/tokens", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(token)))
           .andDo(print())
           .andExpect(status().is(409));

        verify(memberService).registerToken(memberId, token);
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
        return mapper.writeValueAsString(new FireabseTokenDto(token));
    }
}