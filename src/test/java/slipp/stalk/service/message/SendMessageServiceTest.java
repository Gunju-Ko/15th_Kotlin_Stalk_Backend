package slipp.stalk.service.message;

import com.google.firebase.messaging.Message;
import org.junit.Before;
import org.junit.Test;
import slipp.stalk.controller.exceptions.CannotSendMessageException;
import slipp.stalk.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static slipp.stalk.service.message.Response.Result.SUCCESS;

public class SendMessageServiceTest {

    private FakeMessageSender sender;
    private Member from;
    private Member to;

    @Before
    public void setUp() throws Exception {
        sender = new FakeMessageSender();
        from = new Member();
        to = new Member();
        to.addToken("test token1");
        to.addToken("test token2");
    }

    @Test
    public void sendMessages() {
        SendMessageService service = new SendMessageService(sender, new NoOpResponseHandler(), (f, t) -> "unknown", (from, to) -> true);

        service.sendMessages(from, to, "Hello World");

        List<Message> histories = sender.histories();
        // should send message to all tokens
        assertThat(histories.size()).isEqualTo(2);
    }

    @Test
    public void shouldNot_sendMessages_when_canSendMessage_returnFalse() {
        SendMessageService service = new SendMessageService(sender, new NoOpResponseHandler(), (f, t) -> "unknown", (from, to) -> false);

        Throwable t = catchThrowable(() -> service.sendMessages(from, to, "Hello World"));

        assertThat(t).isInstanceOf(CannotSendMessageException.class);
        List<Message> histories = sender.histories();
        // should not send message
        assertThat(histories.size()).isEqualTo(0);
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