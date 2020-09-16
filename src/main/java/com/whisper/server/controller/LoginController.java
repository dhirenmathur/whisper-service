package com.whisper.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import com.whisper.server.constants.Person;
import com.whisper.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestBody Person person) throws InterruptedException, ExecutionException, JsonProcessingException {
        Person user = userService.getUser(person.getUsername());
        String passwordInput = Hashing.sha256()
                .hashString(person.getPassword(), StandardCharsets.UTF_8)
                .toString();

        return user.getPassword().equals(passwordInput);
    }
}
