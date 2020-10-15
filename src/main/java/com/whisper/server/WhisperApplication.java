package com.whisper.poc;

import com.whisper.server.controller.LoginController;
import com.whisper.server.controller.UserController;
import com.whisper.server.service.UserService;
import config.WhisperConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackageClasses = {PingController.class, UserController.class, LoginController.class, WhisperConfig.class})
public class WhisperApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        setRegisterErrorPageFilter(false);
        return application.sources(WhisperApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WhisperApplication.class, args);
    }

}

@RestController
class PingController {
    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }
}


