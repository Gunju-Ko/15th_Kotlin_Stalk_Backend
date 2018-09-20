package slipp.stalk.infra.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

class FirebaseInitializer {
    FirebaseApp initialize(String resourceLocation) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(resourceLocation);
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
            .build();

        return FirebaseApp.initializeApp(options);
    }
}
