-- ============================================================
--  Data awal MSK Production (Tahap Login)
--  Akun admin bawaan: admin / admin123
-- ============================================================

INSERT IGNORE INTO users (username, password, nama, role)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
        'Administrator', 'ADMIN');
