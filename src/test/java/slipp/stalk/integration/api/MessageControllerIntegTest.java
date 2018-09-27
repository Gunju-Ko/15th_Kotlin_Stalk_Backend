package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.CreateMessageDto;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.controller.dto.MessagesDto;
import slipp.stalk.domain.Message;
import slipp.stalk.infra.jpa.repository.MessageRepository;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MessageControllerIntegTest extends IntegTest {

    private final String url = "/members/messages";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void should__return_403_when_have_not_logged_in() {
        ResponseEntity<MessagesDto> response = restTemplate.getForEntity(url, MessagesDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void should__return_message_list() {
        String email = "gunjuko92@gmail.com";
        ResponseEntity<MessagesDto> response = getForEntityWithJwtToken(email, url, MessagesDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        MessagesDto messages = response.getBody();
        assertThat(messages.size()).isEqualTo(5);
    }

    @Test
    public void should__create_message() {
        String email = "gunjuko92@gmail.com";
        String message = "test";

        ResponseEntity<MessageDto> response = postForEntityWithJwtToken(email, url, createBody(message), MessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        MessageDto body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo(message);

        Optional<Message> dbMessage = messageRepository.findById(body.getId());
        assertThat(dbMessage.isPresent()).isTrue();
        assertThat(dbMessage.get().getMessage()).isEqualTo(message);

        deleteMessage(email, body.getId());
        assertThat(messageRepository.findById(body.getId()).isPresent()).isFalse();
    }

    @Test
    public void shouldNot__create_message_when_same_message_already_exist() {
        String email = "gunjuko92@gmail.com";
        String alreadyExistMessage = "점심 먹자";

        ResponseEntity<MessageDto> response = postForEntityWithJwtToken(email,
                                                                        "/members/messages",
                                                                        createBody(alreadyExistMessage),
                                                                        MessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private void deleteMessage(String email, long id) {
        ResponseEntity<Void> response = deleteForEntityWithJwtToken(email, url + "/" + id, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private CreateMessageDto createBody(String message) {
        CreateMessageDto body = new CreateMessageDto();
        body.setMessage(message);
        return body;
    }

}
