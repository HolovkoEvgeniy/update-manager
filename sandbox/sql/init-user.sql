-- admin - admin123

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM profile.users WHERE username = 'admin') THEN
            INSERT INTO profile.users (username, password, email, company, roles)
            VALUES ('root', '$2a$10$7wAKq2n7op0LJbscR62nCOMN02FyTakykIVOC0quV6MncqpaBRGJC', 'admin@example.com',
                    'AdminCompany', ARRAY ['ROLE_ADMIN']::profile.ROLE[]);
        END IF;
    END
$$;



-- user1 - userpassword1
-- user2 - userpassword2
-- user3 - userpassword3
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM profile.users WHERE username = 'user1') THEN
            INSERT INTO profile.users (username, password, email, company, roles)
            VALUES ('user1', '$2a$10$9cLmwQh8ULQcEyMaEZYm4eWZD1HFhxyyeZLszJSWYJublfm5yuYUi', 'user1@example.com',
                    'Company 1', ARRAY['ROLE_USER']::profile.ROLE[]);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM profile.users WHERE username = 'user2') THEN
            INSERT INTO profile.users (username, password, email, company, roles)
            VALUES ('user2', '$2a$10$YynF9CGXuoHYNyLYUkJKU.8CHdRN/ryjmoTOt1ilZ5C314nmVdhzS', 'user2@example.com',
                    'Company 2', ARRAY['ROLE_USER']::profile.ROLE[]);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM profile.users WHERE username = 'user3') THEN
            INSERT INTO profile.users (username, password, email, company, roles)
            VALUES ('user3', '$2a$10$GZiK/C1Wzqj9HrsdbsoVKuTVwP8cYd4nX/UqPIHXT8P/WyihGC8VS', 'user3@example.com',
                    'Company 3', ARRAY['ROLE_USER']::profile.ROLE[]);
        END IF;
    END
$$;


-- -- Добавление записей в firmware_timestamp, только если они отсутствуют
-- Добавление записей в firmware_timestamp для user1
INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 2, 1700000000, 'Model A', 'Serial-1-A', 'v1.0'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-1-A');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 2, 1700001000, 'Model A', 'Serial-1-B', 'v1.1'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-1-B');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 2, 1700002000, 'Model A', 'Serial-1-C', 'v1.2'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-1-C');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 2, 1700003000, 'Model A', 'Serial-1-D', 'v1.3'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-1-D');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 2, 1700004000, 'Model A', 'Serial-1-E', 'v1.4'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-1-E');

-- Добавление записей в firmware_timestamp для user2
INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 3, 1700005000, 'Model B', 'Serial-2-A', 'v1.0'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-2-A');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 3, 1700006000, 'Model B', 'Serial-2-B', 'v1.1'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-2-B');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 3, 1700007000, 'Model B', 'Serial-2-C', 'v1.2'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-2-C');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 3, 1700008000, 'Model B', 'Serial-2-D', 'v1.3'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-2-D');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 3, 1700009000, 'Model B', 'Serial-2-E', 'v1.4'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-2-E');

-- Добавление записей в firmware_timestamp для user3
INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 4, 1700010000, 'Model C', 'Serial-3-A', 'v1.0'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-3-A');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 4, 1700011000, 'Model C', 'Serial-3-B', 'v1.1'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-3-B');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 4, 1700012000, 'Model C', 'Serial-3-C', 'v1.2'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-3-C');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 4, 1700013000, 'Model C', 'Serial-3-D', 'v1.3'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-3-D');

INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version)
SELECT 4, 1700014000, 'Model C', 'Serial-3-E', 'v1.4'
WHERE NOT EXISTS (SELECT 1 FROM device.firmware_timestamp WHERE device_serial = 'Serial-3-E');





