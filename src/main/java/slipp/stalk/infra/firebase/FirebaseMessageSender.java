package slipp.stalk.infra.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import slipp.stalk.service.message.MessageSender;
import slipp.stalk.service.message.Response;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseMessageSender implements MessageSender {

    private final boolean dryRun;
    private final FirebaseMessaging firebaseMessaging;

    FirebaseMessageSender() {
        this(false);
    }

    FirebaseMessageSender(boolean dryRun) {
        this.dryRun = dryRun;
        firebaseMessaging = FirebaseMessaging.getInstance();
    }

    @Override
    public Response send(Message message) {
        try {
            String messageId = firebaseMessaging.send(message, dryRun);
            return new Response(Response.Result.SUCCESS, messageId);
        } catch (FirebaseMessagingException e) {
            return new Response(Response.Result.FAIL, e.getMessage());
        }
    }

    @Override
    public List<Response> send(List<Message> messages) {
        return messages.stream()
                       .map(this::send)
                       .collect(Collectors.toList());
    }
}
