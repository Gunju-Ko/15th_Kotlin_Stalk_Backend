package slipp.stalk.service.message;

import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.Token;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SendMessageService {

    private final MessageSender messageSender;
    private final ResponseHandler responseHandler;
    private final TitleAssignor titleAssignor;

    public SendMessageService(MessageSender messageSender,
                              ResponseHandler responseHandler,
                              TitleAssignor titleAssignor) {
        this.messageSender = messageSender;
        this.responseHandler = responseHandler;
        this.titleAssignor = titleAssignor;
    }

    public void sendMessages(Member from, Member to, String message) {
        String title = titleAssignor.makeTitle(from, to);

        List<Message> messages = createMessages(to.getTokens(), title, message);
        List<Response> responses = messageSender.send(messages);

        responseHandler.handleResponse(responses);
    }

    private List<Message> createMessages(List<Token> tokens, String title, String content) {
        return tokens.stream()
                     .map(t -> Message.builder()
                                      .putData("title", title)
                                      .putData("content", content)
                                      .setToken(t.getValue())
                                      .build())
                     .collect(Collectors.toList());
    }

}
