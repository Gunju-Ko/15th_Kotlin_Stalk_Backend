package slipp.stalk.service.member;

import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class AddDefaultMessagesListener implements CreateMemberListener {

    private final List<String> messages = Arrays.asList("점심 먹자",
                                                        "저녁 먹자",
                                                        "출근함?",
                                                        "YES",
                                                        "NO");
    @Override
    @Transactional
    public void created(Member member) {
        messages.forEach(member::addMessage);
    }
}
