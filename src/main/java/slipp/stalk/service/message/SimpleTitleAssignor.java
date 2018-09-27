package slipp.stalk.service.message;

import org.springframework.stereotype.Component;
import slipp.stalk.domain.Member;

import java.util.Optional;

@Component
public class SimpleTitleAssignor implements TitleAssignor {
    @Override
    public String makeTitle(Member from, Member to) {
        return Optional.ofNullable(from)
                       .map(Member::getName)
                       .orElse("unknown");
    }
}
