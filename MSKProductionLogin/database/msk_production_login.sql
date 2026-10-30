-- ============================================================
--  MSK Production - Database MySQL (Tahap Login)
--  Import file ini melalui phpMyAdmin / MySQL CLI bila ingin
--  membuat database secara manual. Aplikasi juga dapat membuat
--  database secara otomatis saat dijalankan.
--
--  Akun admin bawaan: admin / admin123
-- ============================================================

CREATE DATABASE IF NOT EXISTS msk_production_login CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE msk_production_login;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO users (username, password, nama, role)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
        'Administrator', 'ADMIN');
