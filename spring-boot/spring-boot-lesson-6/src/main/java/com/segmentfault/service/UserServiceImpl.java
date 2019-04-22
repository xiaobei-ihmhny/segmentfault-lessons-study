package com.segmentfault.service;


import com.segmentfault.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service("userService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService {


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {


        Boolean result = jdbcTemplate.execute("INSERT INTO user(name,age) VALUES(?,?);", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());

                return preparedStatement.executeUpdate() > 0;
            }
        });

        return result;
    }
}
