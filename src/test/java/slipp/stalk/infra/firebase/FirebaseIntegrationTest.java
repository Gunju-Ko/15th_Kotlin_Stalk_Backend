package slipp.stalk.infra.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import slipp.stalk.service.messaging.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class FirebaseIntegrationTest {

    private static final String RESOURCE_LOCATION = "classpath:serviceAccountKey.json";
    private FirebaseMessageSender sender;

    @BeforeClass
    public static void setUpClass() throws Exception {
        FirebaseInitializer initializer = new FirebaseInitializer();
        try {
            initializer.initialize(RESOURCE_LOCATION);
        } catch (IllegalStateException ignored) {
        }
    }

    @Before
    public void setUp() throws Exception {
        sender = new FirebaseMessageSender(true);
    }

    @Test
    public void should_success_when_send_message_to_valid_user() throws FirebaseMessagingException {
        String registrationToken = "cvwvUUoYeXM:APA91bH0EnvPXe5u6mb-RDi3ECdQBUZYegIGCGnMup6Ml9BFkzHjAzRD3gPQA8snCg1dqLdVgUnHAZX1xe" +
            "-NhDU9x_Faqtjj4W9QRZsx35e_kYDH1GGvwpyR-3_emrTcVY9nuDw9U-TAX0gmjJQ5Sau_U_kkcvwjMg";

        Message message = createMessage(registrationToken, "title", "content");
        Response response = sender.send(message);
        assertThat(response.getResult()).isEqualTo(Response.Result.SUCCESS);
    }

    @Test
    public void should_fail_when_send_message_to_invalid_user() throws FirebaseMessagingException {
        String invalidRegistrationToken = "cvwvTwwYeXM:APA91bH0EnvPXe5u6mb-RDi3ECdQBUZYegIGCGnMup6Ml9BFkzHjAzRD3gPQA8snCg1dqLdVgUnHAZX1xe" +
            "-NhDU9x_Faqtjj4W9QRZsx35e_kYDH1GGvwpyR-3_emrTcVY9nuDw9U-TC0xa1jJQ5Sau_U_kkcxfqD1";

        Message message = createMessage(invalidRegistrationToken, "title", "content");
        Response response = sender.send(message);
        assertThat(response.getResult()).isEqualTo(Response.Result.FAIL);
        assertThat(response.getDetailMessage()).isEqualTo("Request contains an invalid argument.");
    }

    private Message createMessage(String registrationToken, String title, String content) {
        return Message.builder()
                      .putData("title", title)
                      .putData("content", content)
                      .setToken(registrationToken)
                      .build();
    }
}
