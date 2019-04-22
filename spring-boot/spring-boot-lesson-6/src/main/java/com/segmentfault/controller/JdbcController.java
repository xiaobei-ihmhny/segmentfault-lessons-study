package com.segmentfault.controller;

import com.segmentfault.domain.User;
import com.segmentfault.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaobei
 * @version 1.0
 * @className JdbcController
 * @description 测试
 * @date 2019-04-21 08:49:49
 */
@RestController
public class JdbcController {

    @Resource
    private DataSource dataSource;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserService userService;

    /**
     * jdbc方式
     * @param id
     * @return
     */
    @RequestMapping("/user/get")
    public Map<String,Object> getUser(@RequestParam(value = "id", defaultValue = "1") int id) {
        Map<String, Object> map = new HashMap<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            String sql = "SELECT id, username FROM `user` WHERE id = ?";
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT id, username FROM `user` WHERE id =" + id);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id_ = resultSet.getInt("id");
                String username = resultSet.getString("username");
                map.put("id_",id);
                map.put("username",username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * jdbcTemplate方式
     * @param user
     * @return
     */
    @RequestMapping("/user/add2")
    @ResponseBody
    public Map<String,Object> addUser(@RequestBody User user) {
        Map<String,Object> map = new HashMap<>();

        Boolean result = jdbcTemplate.execute("INSERT INTO user(name,age) VALUES(?,?);", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());

                return preparedStatement.executeUpdate() > 0;
            }
        });

        map.put("success",result);

        return map;
    }

    @RequestMapping("/user/add")
    @ResponseBody
    public Map<String,Object> saveUser(@RequestBody User user) {
        Map<String,Object> map = new HashMap<>();

        map.put("success",userService.save(user));

        return map;

    }

}
