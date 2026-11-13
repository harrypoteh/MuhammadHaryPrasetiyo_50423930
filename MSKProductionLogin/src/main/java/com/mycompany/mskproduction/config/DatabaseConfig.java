package com.mycompany.mskproduction.config;

import com.mycompany.mskproduction.db.Database;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;

/**
 * Menyediakan JdbcTemplate yang memakai koneksi dari kelas Database.
 * Setiap query membuka koneksi lewat Database.getConnection(), jadi
 * perubahan dari "Pengaturan Database" langsung berlaku tanpa
 * membuat ulang JdbcTemplate.
 */
public final class DatabaseConfig {

    private static final JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(new AbstractDataSource() {
        @Override
        public Connection getConnection() throws SQLException {
            return Database.getConnection();
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return Database.getConnection();
        }
    });

    private DatabaseConfig() {
    }

    public static JdbcTemplate getJdbcTemplate() {
        return JDBC_TEMPLATE;
    }
}