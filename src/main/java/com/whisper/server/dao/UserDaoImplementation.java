package com.whisper.server.dao;

import com.whisper.server.entity.User;
import com.whisper.server.mapper.UserMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class UserDaoImplementation implements UserDao{

    public UserDaoImplementation(NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    NamedParameterJdbcTemplate template;

    @Override
    public List<User> findAll() {
        return template.query("select * from user", new UserMapper());
    }
}
