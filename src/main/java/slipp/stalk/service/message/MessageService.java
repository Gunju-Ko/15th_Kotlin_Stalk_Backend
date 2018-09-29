package slipp.stalk.service.message;

import org.springframework.stereotype.Component;
import slipp.stalk.controller.exceptions.MemberNotFoundException;
import slipp.stalk.controller.exceptions.MessageNotFoundException;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.Message;
import slipp.stalk.infra.jpa.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MessageService {

    private final MemberRepository memberRepository;
    private final SendMessageService sendMessageService;

    public MessageService(MemberRepository memberRepository,
                          SendMessageService sendMessageService) {
        this.memberRepository = memberRepository;
        this.sendMessageService = sendMessageService;
    }

    public List<Message> getMessages(long memberId) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        return member.getMessages();
    }

    @Transactional
    public Message createMessage(long memberId, String message) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        return member.addMessage(message);
    }

    @Transactional
    public void delete(long memberId, long messageId) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        member.deleteMessage(messageId);
    }

    @Transactional
    public Message updateMessage(long memberId, long messageId, String message) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        return member.updateMessage(messageId, message);
    }

    public void sendMessages(Member from, long toMemberId, long messageId) {
        Member to = memberRepository.findById(toMemberId)
                                    .orElseThrow(MemberNotFoundException::new);

        Message message = from.getMessage(messageId)
                              .orElseThrow(MessageNotFoundException::new);

        sendMessageService.sendMessages(from, to, message.getMessage());
    }
}
