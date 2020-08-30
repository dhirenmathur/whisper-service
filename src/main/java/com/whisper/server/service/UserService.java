package com.whisper.server.service;

import com.whisper.server.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
