package com.whisper.server.controller;

import com.whisper.server.entity.User;
import com.whisper.server.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/userApp")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping(value = "/userList")
    public List<User> getUsers() {
        return userService.findAll();
    }
}
