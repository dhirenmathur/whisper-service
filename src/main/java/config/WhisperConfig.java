package config;

import com.whisper.server.service.ActionService;
import com.whisper.server.service.SocketTextHandler;
import com.whisper.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhisperConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public ActionService actionService() {
        return new ActionService();
    }

    @Bean
    public SocketTextHandler socketTextHandler(){
        return new SocketTextHandler();
    }
}
