package slipp.stalk.infra.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import slipp.stalk.service.messaging.MessageSender;
import slipp.stalk.service.messaging.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    static final String RESOURCE_LOCATION = "serviceAccountKey.json";

    @Bean
    public MessageSender messageSender() {
        if (FirebaseApp.getApps().isEmpty()) {
            log.warn("Using FakeMessageSender, You cannot send firebase message");
            return new FakeMessageSender();
        }
        return new FirebaseMessageSender();
    }

    public static class FirebaseInitializerInvoker implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
        @Override
        public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
            FirebaseInitializer initializer = new FirebaseInitializer();
            try {
                initializer.initialize(RESOURCE_LOCATION);
                log.info("Firebase app is initialized");
            } catch (IllegalStateException e) {
                log.warn("Firebase app has already been initialized.");
            } catch (IOException e) {
                log.warn("Exception is occur while initialize firebase app : {}", e.getMessage());
            }
        }
    }

    public class FakeMessageSender implements MessageSender {

        @Override
        public Response send(Message message) {
            return new Response(Response.Result.FAIL, "Fail to send message");
        }

        @Override
        public List<Response> send(List<Message> messages) {
            return messages.stream()
                           .map(this::send)
                           .collect(Collectors.toList());
        }
    }

}