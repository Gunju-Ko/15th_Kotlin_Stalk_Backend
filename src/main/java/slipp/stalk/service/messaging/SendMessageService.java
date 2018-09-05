package slipp.stalk.service.messaging;

import org.springframework.stereotype.Component;

@Component
public class SendMessageService {

    private final MessageSender messageSender;

    public SendMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public Response sendMessage(long id, String message) {
        // TODO : implementation
        return null;
    }
}
