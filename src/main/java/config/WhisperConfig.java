package config;

import com.whisper.server.service.CallInitiatorService;
import com.whisper.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhisperConfig {

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public CallInitiatorService callInitiatorService(){
        return new CallInitiatorService();
    }

}
