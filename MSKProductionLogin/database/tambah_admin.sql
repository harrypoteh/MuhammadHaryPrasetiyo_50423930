-- ============================================================
--  Menambah admin baru - MSK Production (Tahap Login)
--
--  PENTING:
--  Kolom password harus berisi hash SHA-256, bukan password asli.
--  Gunakan fungsi SHA2('password', 256) seperti contoh di bawah.
--
--  Langkah:
--  1. Ganti username, password, dan nama sesuai kebutuhan.
--  2. Jalankan file ini melalui phpMyAdmin (tab SQL) atau MySQL CLI.
-- ============================================================

USE msk_production_login;

INSERT INTO users (username, password, nama, role)
VALUES ('admin4', SHA2('passwordbaru', 256), 'Admin 4', 'ADMIN');

-- Contoh menambah beberapa admin sekaligus:
-- INSERT INTO users (username, password, nama, role) VALUES
-- ('admin5', SHA2('rahasia5', 256), 'Admin 5', 'ADMIN'),
-- ('admin6', SHA2('rahasia6', 256), 'Admin 6', 'ADMIN');
