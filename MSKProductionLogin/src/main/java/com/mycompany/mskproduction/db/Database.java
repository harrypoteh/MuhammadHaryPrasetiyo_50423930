package com.mycompany.mskproduction.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Pengelola koneksi database MySQL.
 * <p>
 * Pengaturan disimpan di {@code ~/.mskproduction-login/database.properties}
 * sehingga pengguna dapat mengganti host/user/password tanpa mengubah kode
 * program.
 * Saat aplikasi dijalankan, database dan tabel dibuat otomatis bila belum ada.
 */
public final class Database {

    private static final Path CONFIG_DIR = Paths.get(System.getProperty("user.home"), ".mskproduction-login");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("database.properties");
    private static final Properties CONFIG = new Properties();

    static {
        loadConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Driver MySQL Connector/J tidak ditemukan.", ex);
        }
    }

    private Database() {
    }

    private static void loadConfig() {
        CONFIG.setProperty("host", "localhost");
        CONFIG.setProperty("port", "3306");
        CONFIG.setProperty("database", "msk_production_login");
        CONFIG.setProperty("user", "root");
        CONFIG.setProperty("password", "");
        if (Files.exists(CONFIG_FILE)) {
            try (InputStream input = Files.newInputStream(CONFIG_FILE)) {
                CONFIG.load(input);
            } catch (IOException ex) {
                System.err.println("Gagal membaca konfigurasi database: " + ex.getMessage());
            }
        }
    }

    public static String getHost() {
        return CONFIG.getProperty("host", "localhost");
    }

    public static int getPort() {
        try {
            return Integer.parseInt(CONFIG.getProperty("port", "3306"));
        } catch (NumberFormatException ex) {
            return 3306;
        }
    }

    public static String getDatabaseName() {
        return CONFIG.getProperty("database", "msk_production_login");
    }

    public static String getUser() {
        return CONFIG.getProperty("user", "root");
    }

    public static String getPassword() {
        return CONFIG.getProperty("password", "");
    }

    public static String getDescription() {
        return getHost() + ":" + getPort() + " / " + getDatabaseName();
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE.toString();
    }

    private static String connectionUrl(String host, int port, String database) {
        return "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta"
                + "&useUnicode=true&characterEncoding=UTF-8";
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl(getHost(), getPort(), getDatabaseName()),
                getUser(), getPassword());
    }

    /**
     * Membuat database, tabel, akun admin, dan data contoh bila belum tersedia.
     */
    public static synchronized void bootstrap() throws SQLException, IOException {
        String database = getDatabaseName();
        if (!database.matches("[A-Za-z0-9_]+")) {
            throw new SQLException("Nama database hanya boleh berisi huruf, angka, dan garis bawah.");
        }
        try (Connection server = DriverManager.getConnection(
                connectionUrl(getHost(), getPort(), ""), getUser(), getPassword());
                Statement statement = server.createStatement()) {
            statement.execute("CREATE DATABASE IF NOT EXISTS `" + database
                    + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }
        try (Connection connection = getConnection()) {
            runScript(connection, readResource("/db/schema.sql"));
            if (isEmpty(connection, "users")) {
                runScript(connection, readResource("/db/seed.sql"));
            }
        }
    }

    public static void testConnection(String host, int port, String user, String password) throws SQLException {
        int previous = DriverManager.getLoginTimeout();
        DriverManager.setLoginTimeout(5);
        try (Connection connection = DriverManager.getConnection(
                connectionUrl(host, port, ""), user, password)) {
            connection.isValid(5);
        } finally {
            DriverManager.setLoginTimeout(previous);
        }
    }

    /**
     * Menyimpan konfigurasi baru lalu menyiapkan database.
     */
    public static void saveConfig(String host, int port, String database, String user, String password)
            throws IOException, SQLException {
        CONFIG.setProperty("host", host);
        CONFIG.setProperty("port", String.valueOf(port));
        CONFIG.setProperty("database", database);
        CONFIG.setProperty("user", user);
        CONFIG.setProperty("password", password);
        Files.createDirectories(CONFIG_DIR);
        try (OutputStream output = Files.newOutputStream(CONFIG_FILE)) {
            CONFIG.store(output, "Konfigurasi database MSK Production");
        }
        bootstrap();
    }

    private static boolean isEmpty(Connection connection, String table) throws SQLException {
        try (Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {
            return result.next() && result.getInt(1) == 0;
        }
    }

    private static String readResource(String name) throws IOException {
        try (InputStream input = Database.class.getResourceAsStream(name)) {
            if (input == null) {
                throw new IOException("Berkas " + name + " tidak ditemukan di dalam aplikasi.");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static void runScript(Connection connection, String script) throws SQLException {
        StringBuilder current = new StringBuilder();
        for (String line : script.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                continue;
            }
            current.append(line).append('\n');
            if (trimmed.endsWith(";")) {
                execute(connection, current.toString());
                current.setLength(0);
            }
        }
        if (!current.toString().isBlank()) {
            execute(connection, current.toString());
        }
    }

    private static void execute(Connection connection, String sql) throws SQLException {
        String statement = sql.trim();
        if (statement.endsWith(";")) {
            statement = statement.substring(0, statement.length() - 1);
        }
        if (!statement.isBlank()) {
            try (Statement executor = connection.createStatement()) {
                executor.execute(statement);
            }
        }
    }
}
