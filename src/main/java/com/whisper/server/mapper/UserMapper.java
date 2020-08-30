package com.whisper.server.mapper;

import com.whisper.server.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getString("userId"));
        user.setUserName(resultSet.getString("userName"));
        user.setUserEmail(resultSet.getString("userEmail"));

        return user;
    }
}
