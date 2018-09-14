package slipp.stalk.service.messaging;

import com.google.firebase.messaging.Message;
import org.junit.Before;
import org.junit.Test;
import slipp.stalk.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static slipp.stalk.service.messaging.Response.Result.SUCCESS;

public class SendMessageServiceTest {

    private SendMessageService service;
    private FakeMessageSender sender;

    @Before
    public void setUp() throws Exception {
        sender = new FakeMessageSender();
        service = new SendMessageService(sender, new NoOpResponseHandler(), (f, t) -> "unknown");
    }

    @Test
    public void sendMessages() {
        Member from = new Member();
        Member to = new Member();
        String token1 = "test token1";
        String token2 = "test token2";
        to.addToken(token1);
        to.addToken(token2);

        service.sendMessages(from, to, "Hello World");

        List<Message> histories = sender.histories();

        // should send message to all tokens
        assertThat(histories.size()).isEqualTo(2);
    }

    private static class FakeMessageSender implements MessageSender {
        private List<Message> histories = new ArrayList<>();

        @Override
        public Response send(Message message) {
            histories.add(message);
            return new Response(SUCCESS, "mock result");
        }

        @Override
        public List<Response> send(List<Message> messages) {
            return messages.stream()
                           .map(this::send)
                           .collect(Collectors.toList());
        }

        public List<Message> histories() {
            return histories;
        }
    }
}