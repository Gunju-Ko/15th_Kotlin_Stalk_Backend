package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.CreateMessageDto;
import slipp.stalk.controller.dto.MessageDto;
import slipp.stalk.controller.dto.MessagesDto;
import slipp.stalk.controller.dto.ResponseDto;
import slipp.stalk.controller.exceptions.MessageNotFoundException;
import slipp.stalk.controller.exceptions.UpdateMessageDto;
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
        ResponseDto<MessagesDto> response = getForEntityWithJwtToken(email,
                                                                     url,
                                                                     new ParameterizedTypeReference<ResponseDto<MessagesDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        MessagesDto messages = response.getData();
        assertThat(messages.size()).isEqualTo(5);
    }

    @Test
    public void should__create_message() {
        String email = "gunjuko92@gmail.com";
        String message = "test";

        ResponseDto<MessageDto> response = postForEntityWithJwtToken(email,
                                                                     url,
                                                                     createBody(message),
                                                                     new ParameterizedTypeReference<ResponseDto<MessageDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);

        MessageDto body = response.getData();

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

        ResponseDto<MessageDto> response = postForEntityWithJwtToken(email,
                                                                     url,
                                                                     createBody(alreadyExistMessage),
                                                                     new ParameterizedTypeReference<ResponseDto<MessageDto>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
    }

    @Test
    public void should__update_message() {
        String email = "gunjuko92@gmail.com";
        String updateMessage = "Test update!";
        long messageId = 3;

        ResponseDto<MessageDto> response = putForEntityWithJwtToken(email,
                                                                    url + "/" + messageId,
                                                                    new UpdateMessageDto(updateMessage),
                                                                    new ParameterizedTypeReference<ResponseDto<MessageDto>>() {});

        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getMessage()).isEqualTo(updateMessage);

        Message dbMessage = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        assertThat(dbMessage.getMessage()).isEqualTo(updateMessage);
    }

    private void deleteMessage(String email, long id) {
        ResponseDto<Void> response = deleteForEntityWithJwtToken(email, url + "/" + id, null, Void.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
    }

    private CreateMessageDto createBody(String message) {
        CreateMessageDto body = new CreateMessageDto();
        body.setMessage(message);
        return body;
    }

}
