package com.whisper.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import com.whisper.server.constants.Person;
import com.whisper.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{name}")
    public Object getUser(@PathVariable(value = "name") String name) throws InterruptedException, ExecutionException, JsonProcessingException {
        return userService.getUser(name);
    }

    @PostMapping("/createUser")
    public String postUser(@RequestBody Person person) throws InterruptedException, ExecutionException, JsonProcessingException {
        if (!ObjectUtils.isEmpty(userService.getUser(person.getUsername()))) {
            return "0";
        }
        String sha256hex = Hashing.sha256()
                .hashString(person.getPassword(), StandardCharsets.UTF_8)
                .toString();
        person.setPassword(sha256hex);
        return userService.createUser(person);

    }
}

