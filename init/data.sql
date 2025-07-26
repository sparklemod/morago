INSERT INTO role (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_TRANSLATOR'),
    (3, 'ROLE_USER');

-- USERS (пароль как email)
INSERT INTO user (id, phone, password, first_name, last_name, email, is_active, balance, image_file_id, created_at)
VALUES
    (1, '+70000000002', '$2a$12$9cay2MFJR3ePSw.n5bTZbOoh.BIUMfX04r0hZ7wqgFTz/lEfaEjym', 'Elena',  'Ivanova',  'elena2@example.com', true, 1000, NULL, NOW()),
    (2, '+70000000001', '$2a$12$QO4uj4lpWsdALxJmhMWdc.gWi/DlZUBvx0HlcJ.10iI8xnNTGME5u', 'Ivan',   'Petrov',   'ivan1@example.com', true, 1000, NULL, NOW()),
    (3, '+70000000003', '$2a$12$lLNDZTwp/jXwcbUsx1iO3OmemkX31qMVoqiFl7TUbgizG/Z9WUYOu', 'Maxim',  'Kozlov',   'maxim3@example.com', false, 1000, NULL, NOW()),
    (4, '+70000000004', '$2a$12$AGw3IbQJH9dTSUMjLQpVb.VIany4W1Mt25rh5WjMpeO5.Z4UX4xsu', 'Olga',   'Smirnova', 'olga4@example.com', true, 1000, NULL, NOW()),
    (5, '+70000000005', '$2a$12$puq7a.o0aT6QRK6ygiid8.OtI7kANzye5Yo7zOT9r33iBlZpaCfKu', 'Dmitry', 'Volkov',   'dmitry5@example.com', true, 1000, NULL, NOW()),
    (6, '+70000000006', '$2a$12$z89w4eZaa7qm1vDLR2juuO8xryKI0AmimqTL3mdqM.81i70d.16Yq', 'Anna',   'Sidorova', 'anna6@example.com', true, 1000, NULL, NOW()),
    (7, '+70000000007', '$2a$12$0sqFnlBUnv9DeleZMiKqlujw.zici1EhginSdq1xyr/LUJhhnmvsK', 'Nikolay','Morozov',  'nikolay7@example.com', true, 1000, NULL, NOW()),
    (8, '+70000000008', '$2a$12$QXv8RFRCMg0pzABu31xwg.QMCyKNEoz30IaP9EIbZou.TZlRIB4qK', 'Svetlana','Popova',  'sveta8@example.com', true, 1000, NULL, NOW()),
    (9, '+70000000009', '$2a$12$R6fadWCToM192wtt.pRZW.SInB.eehkGHvo0m1eo/Tt8/ijLjQfR.', 'Alexey', 'Fedorov',  'alex9@example.com', true, 1000, NULL, NOW()),
    (10,'+70000000010', '$2a$12$pS8NjvSrOR8il.PJ6EM0We14gZyyDiJ0RpOEEmGhmwFTTsIdcmZv.','Irina',  'Alekseeva','irina10@example.com', false, 1000, NULL, NOW()),
    (11,'+70000000011', '$2a$12$7LRJ9UgeP0eO9a9Riex0LepJavddaLoiEhzGuyTRZLsOh/YFQ3Hby','Oleg',   'Orlov',    'oleg11@example.com', true, 1000, NULL, NOW()),
    (12,'+70000000012', '$2a$12$C4Mzq5oeJze4j2wsp2LfoefOjZvnHXpMTBm833IiL1Ll7A0An/yQe','Maria',  'Belova',   'maria12@example.com', true, 1000, NULL, NOW());


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


INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 3),
    (9, 3),
    (10, 3),
    (11, 3),
    (12, 3);