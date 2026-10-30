package com.mycompany.mskproduction.dao;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.model.User;
import com.mycompany.mskproduction.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, nama, role FROM users WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username.trim());
            statement.setString(2, PasswordUtil.sha256(password));
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(result.getInt("id"), result.getString("username"),
                            result.getString("nama"), result.getString("role"));
                }
            }
        }
        return null;
    }
}
