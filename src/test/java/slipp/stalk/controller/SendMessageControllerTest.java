package slipp.stalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.domain.Member;
import slipp.stalk.service.MemberService;
import slipp.stalk.service.messaging.SendMessageService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SendMessageControllerTest {

    private MockMvc mvc;
    private SendMessageService sendMessageService;
    private MemberService memberService;

    @Before
    public void setUp() {
        sendMessageService = Mockito.mock(SendMessageService.class);
        memberService = Mockito.mock(MemberService.class);

        mvc = MockMvcBuilders.standaloneSetup(new SendMessageController(sendMessageService, memberService))
                             .setControllerAdvice(new RestApiControllerAdvice())
                             .build();
    }

    @Test
    public void should_send_message() throws Exception {
        MessageDto message = new MessageDto("메시지 내용");
        long id = 1;
        doReturn(Optional.of(new Member()))
            .when(memberService).get(id);

        mvc.perform(post(createUrl(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(message)))
           .andDo(print())
           .andExpect(status().is(204));

        verify(sendMessageService).sendMessages(any(), any(), any());
    }

    @Test
    public void should_return_400_when_message_is_empty() throws Exception {
        MessageDto message = new MessageDto("");
        long id = 1;

        mvc.perform(post(createUrl(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(message)))
           .andDo(print())
           .andExpect(status().is(400));
    }

    private String createUrl(long id) {
        return String.format("/members/%d/message", id);
    }

    private String body(MessageDto message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(message);
    }
}