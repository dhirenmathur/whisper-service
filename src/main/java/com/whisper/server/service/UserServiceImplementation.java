package com.whisper.server.service;

import com.whisper.server.dao.UserDao;
import com.whisper.server.entity.User;

import javax.annotation.Resource;
import java.util.List;

public class UserServiceImplementation implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
