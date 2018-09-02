package slipp.stalk.service.messaging;

import com.google.firebase.messaging.Message;

public interface MessageSender {
    Response send(Message message);
}
