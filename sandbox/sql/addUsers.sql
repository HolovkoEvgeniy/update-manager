-- Заполняем таблицу profile.users с зашифрованными паролями
INSERT INTO profile.users (username, password, email, company, roles)
VALUES
-- Администратор
('admin', '$2a$10$adminEncryptedHash...', 'admin@example.com', 'AdminCompany', ARRAY['ROLE_ADMIN']),
-- Обычные пользователи
('user1', '$2a$10$user1EncryptedHash...', 'user1@example.com', 'Company A', ARRAY['ROLE_USER']),
('user2', '$2a$10$user2EncryptedHash...', 'user2@example.com', 'Company B', ARRAY['ROLE_USER']),
('user3', '$2a$10$user3EncryptedHash...', 'user3@example.com', 'Company C', ARRAY['ROLE_USER']);

-- Заполняем таблицу device.firmware_timestamp для каждого пользователя
INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
VALUES
-- Записи для user1
(2, 1700000000, 'Model A', 'Serial-1-A', 'v1.0'),
(2, 1700001000, 'Model B', 'Serial-1-B', 'v1.1'),
(2, 1700002000, 'Model C', 'Serial-1-C', 'v2.0'),
(2, 1700003000, 'Model D', 'Serial-1-D', 'v2.1'),
(2, 1700004000, 'Model E', 'Serial-1-E', 'v3.0'),

-- Записи для user2
(3, 1700000000, 'Model A', 'Serial-2-A', 'v1.0'),
(3, 1700001000, 'Model B', 'Serial-2-B', 'v1.1'),
(3, 1700002000, 'Model C', 'Serial-2-C', 'v2.0'),
(3, 1700003000, 'Model D', 'Serial-2-D', 'v2.1'),
(3, 1700004000, 'Model E', 'Serial-2-E', 'v3.0'),

-- Записи для user3
(4, 1700000000, 'Model A', 'Serial-3-A', 'v1.0'),
(4, 1700001000, 'Model B', 'Serial-3-B', 'v1.1'),
(4, 1700002000, 'Model C', 'Serial-3-C', 'v2.0'),
(4, 1700003000, 'Model D', 'Serial-3-D', 'v2.1'),
(4, 1700004000, 'Model E', 'Serial-3-E', 'v3.0');
