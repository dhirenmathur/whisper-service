package com.whisper.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WhisperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhisperApplication.class, args);
    }

}

@RestController
class HelloWorldController {
    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }
}
