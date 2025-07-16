CREATE DATABASE IF NOT EXISTS morago;
USE morago;

-- USERS (пароль как email)
INSERT INTO users (id, phone, password, first_name, last_name, email, is_active, balance, image_id, role, created_at)
VALUES
    (1, '+70000000002', '$2a$10$Zy/kTR7vh1rUMpb4TSJ9cOgXYnO1m6LfF9Ef7Am.O4dmB.SXTD59K', 'Elena',  'Ivanova',  'elena2@example.com', true, 1000, NULL, 'ADMIN', NOW()),
    (2, '+70000000001', '$2a$10$Hb8QQEGx.1MblFb2llEQEOt/Cw6lydxu63DOUasPq2FvO1KhOGJ8C', 'Ivan',   'Petrov',   'ivan1@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (3, '+70000000003', '$2a$10$KJDpDZRCQ4N62DRZB8ll8.xUw/IlPyDB.ju8m7rMPoxChky0iYe4S', 'Maxim',  'Kozlov',   'maxim3@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (4, '+70000000004', '$2a$10$NDMvKOsSox84cc6fFu19hOUDi1IXyaPP2PKZt6T3c6KGVGya3jwsu', 'Olga',   'Smirnova', 'olga4@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (5, '+70000000005', '$2a$10$fp8VtAsghDhcmErL0sofyOhmzZgOi/YXLdjsVXKZMQB5cp5A5Id0e', 'Dmitry', 'Volkov',   'dmitry5@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (6, '+70000000006', '$2a$10$ggFcP38NqQlNeKZ8sE/qGevGPc3VRz2Y7XjUtaW7QH6Q1gTQWPSZa', 'Anna',   'Sidorova', 'anna6@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (7, '+70000000007', '$2a$10$7VtrTRg/6kpFS8hfLnF/suT97nY8Vq2SgX.QIbFhPVNpyZy8v1BaK', 'Nikolay','Morozov',  'nikolay7@example.com', true, 1000, NULL, 'TRANSLATOR', NOW()),
    (8, '+70000000008', '$2a$10$2vOmLDdBd66ERjZWjk/8c.yg7Iu6D7ApmLvcq.gqCNqAlLRKR3ueu', 'Svetlana','Popova',  'sveta8@example.com', true, 1000, NULL, 'USER', NOW()),
    (9, '+70000000009', '$2a$10$FBR7m66HvL3GzRJHDAeRYuAtKeCAUzP3UNzhGa6DToEpUZ.7q7cfu', 'Alexey', 'Fedorov',  'alex9@example.com', true, 1000, NULL, 'USER', NOW()),
    (10,'+70000000010', '$2a$10$XDmefN6kLMKNtF7nSFBMQuLHVzj.YeF7iOh1fxG0fYCMu2pj7Y4.u','Irina',  'Alekseeva','irina10@example.com', true, 1000, NULL, 'USER', NOW()),
    (11,'+70000000011', '$2a$10$zQrqm1vJtu.xPYHLWKn2w.lFZz3PYcyLMKhr37t3zI.1XDNLMojUS','Oleg',   'Orlov',    'oleg11@example.com', true, 1000, NULL, 'USER', NOW()),
    (12,'+70000000012', '$2a$10$tctZmTIImnTUMV8OMKH8U.2rFHpT1E5Avrqt2fTFjbnk6TxXkV9ca','Maria',  'Belova',   'maria12@example.com', true, 1000, NULL, 'USER', NOW());

-- ADMIN
INSERT INTO admin (id, is_super_admin)
VALUES
    (1, true);

-- TRANSLATOR
INSERT INTO translator (id, date_of_birth, is_online, level_of_korean, memo)
VALUES
    (2, '1985-05-12', false, 4, 'Test memo 2'),
    (3, '1992-07-15', true, 5, 'Test memo 3'),
    (4, '1994-03-21', false, 2, 'Test memo 4'),
    (5, '1991-09-09', true, 3, 'Test memo 5'),
    (6, '1988-11-30', false, 1, 'Test memo 6'),
    (7, '2000-11-30', false, 3, 'Test memo 7');

-- USER PROFILE
INSERT INTO user_profile (id, is_debtor, is_free_call_made)
VALUES
    (8, false, true),
    (9, false, false),
    (10, true, false),
    (11, false, false),
    (12, true, true);