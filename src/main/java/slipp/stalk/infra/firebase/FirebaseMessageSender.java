package slipp.stalk.infra.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import slipp.stalk.service.messaging.MessageSender;
import slipp.stalk.service.messaging.Response;

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
}
