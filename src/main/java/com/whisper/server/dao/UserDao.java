package com.whisper.server.dao;

import com.whisper.server.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
}
