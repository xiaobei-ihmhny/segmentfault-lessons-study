package com.segmentfault.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
