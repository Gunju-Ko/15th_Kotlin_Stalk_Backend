package slipp.stalk.service.messaging;

import com.google.firebase.messaging.Message;

import java.util.List;

public interface MessageSender {
    Response send(Message message);

    List<Response> send(List<Message> messages);
}
