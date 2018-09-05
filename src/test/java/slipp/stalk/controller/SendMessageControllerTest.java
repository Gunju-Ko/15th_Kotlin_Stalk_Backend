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
import slipp.stalk.service.messaging.SendMessageService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SendMessageControllerTest {

    private MockMvc mvc;
    private SendMessageService sendMessageService;

    @Before
    public void setUp() throws Exception {
        sendMessageService = Mockito.mock(SendMessageService.class);
        mvc = MockMvcBuilders.standaloneSetup(new SendMessageController(sendMessageService))
                             .setControllerAdvice(new RestApiControllerAdvice())
                             .build();
    }

    @Test
    public void should_send_message() throws Exception {
        MessageDto message = new MessageDto("메시지 내용");
        long id = 1;

        mvc.perform(post(createUrl(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(message)))
           .andDo(print())
           .andExpect(status().is(204));

        verify(sendMessageService).sendMessage(id, message.getMessage());
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