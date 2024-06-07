package com.group.libraryapp.repository.user;

import com.group.libraryapp.UserResponse;
import com.group.libraryapp.controller.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(String name, int age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * from user";
        //        return jdbcTemplate.query(sql, (rs, rowNum) -> {
        //            long id = rs.getLong("id");
        //            String name = rs.getString("name");
        //            int age = rs.getInt("age");
        //            return new UserResponse(id, new User(name, age));
        //        });
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
                    // 조회한 유저 정보를 UserResponse로 변환
                    @Override
                    public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                        long id = rs.getLong("id");
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        return new UserResponse(id, name, age);
                    }
                }
        );
    }

    public void updateUserName(String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }

    public boolean isUserNotExist(long id) {
        String readSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rownum) -> 0, id).isEmpty();
    }

    public boolean isUserNotExist(String name) {
        String readSql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(readSql, (rs, rownum) -> 0, name).isEmpty();
    }
}
