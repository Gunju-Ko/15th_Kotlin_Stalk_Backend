package slipp.stalk.infra.firebase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import slipp.stalk.service.messaging.MessageSender;

import java.io.IOException;

@Configuration
public class FirebaseConfiguration {

    private static final String RESOURCE_LOCATION = "classpath:serviceAccountKey.json";

    @Bean
    public MessageSender messageSender() {
        return new FirebaseMessageSender();
    }

    @Slf4j
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
                log.error("Exception is occur while initialize firebase app");
                throw new IllegalStateException(e);
            }
        }
    }
}