package config;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.whisper.server.service.UserService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;

@Configuration
public class FirebaseConfig extends FirebaseInitializer {

    @Bean
    public Firestore getDb() {
        return FirestoreClient.getFirestore();
    }
}
