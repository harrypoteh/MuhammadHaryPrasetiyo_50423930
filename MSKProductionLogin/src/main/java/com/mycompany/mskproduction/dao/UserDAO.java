
package com.mycompany.mskproduction.dao;

import com.mycompany.mskproduction.model.User;
import com.mycompany.mskproduction.util.PasswordUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User authenticate(String username, String password) {

        String sql = "SELECT id, username, nama, role FROM users "
                   + "WHERE username = ? AND password = ?";

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("nama"),
                rs.getString("role")
            ),
            username.trim(),
            PasswordUtil.sha256(password)
        ).stream().findFirst().orElse(null);
    }
}

