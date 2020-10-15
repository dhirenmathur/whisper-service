package com.whisper.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import com.whisper.server.constants.Person;
import com.whisper.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.DefaultListSelectionModel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Boolean login(@RequestBody Person person) throws InterruptedException, ExecutionException, JsonProcessingException {
        Person user = userService.getUser(person.getUsername());
        if(ObjectUtils.isEmpty(user))
            return null;

        return true;

    }
}
