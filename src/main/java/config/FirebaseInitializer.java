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
        //comment out lines 20-23 before commiting and do not commit the secrets file
        /*
        FileInputStream serviceAccount =
                new FileInputStream("./src/main/service_account_pk.json");
        GoogleCredentials cred = GoogleCredentials.fromStream(serviceAccount);
*/

        //comment out the following line while testing locally
        GoogleCredentials cred = GoogleCredentials.getApplicationDefault();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(cred)
                .setDatabaseUrl("https://whisper-backend.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
    }


}
