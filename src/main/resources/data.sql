DELETE FROM translator;
DELETE FROM user_profile;
DELETE FROM users;

INSERT INTO users (phone, password, role, balance, is_active, created_at)
VALUES ('test_phone', '$2a$10$6cKjIBDNiuF2PrRucaWNiuVxXCxSOQxM2abQtAOX6zU1NomLcmv1S', 'ADMIN'
       , 0, 1, CURRENT_TIMESTAMP);