package slipp.stalk.service.messaging;

import slipp.stalk.domain.Member;

public class MessageService {

    private final MessageSender messageSender;

    public MessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public Response sendMessage(Member member, String title, String message) {
        // TODO : implementation
        return null;
    }
}
