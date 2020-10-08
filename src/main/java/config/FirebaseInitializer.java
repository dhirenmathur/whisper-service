package config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {

    @PostConstruct
    public void initialize() throws IOException {

        //FileInputStream serviceAccount = new FileInputStream("./src/main/service_account_pk.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                //.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://whisper-db.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
    }


}
