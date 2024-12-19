DROP SCHEMA IF EXISTS profile cascade;
DROP SCHEMA IF EXISTS device cascade;

CREATE SCHEMA IF NOT EXISTS profile;
CREATE SCHEMA IF NOT EXISTS device;

CREATE TYPE profile.ROLE AS ENUM ('ROLE_ADMIN', 'ROLE_USER');

CREATE TABLE profile.users
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(255)   NOT NULL UNIQUE,
    password    VARCHAR(255)   NOT NULL,
    email       VARCHAR(320)   NOT NULL UNIQUE,
    company     VARCHAR(255),
    roles       profile.ROLE[] NOT NULL,
    create_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE device.firmware_timestamp
(
    id               SERIAL PRIMARY KEY,
    user_id          INTEGER REFERENCES profile.users (id) ON DELETE CASCADE,
    unixtime         BIGINT       NOT NULL,
    device_model     VARCHAR(255) NOT NULL,
    device_serial    VARCHAR(255) NOT NULL,
    firmware_version VARCHAR(255) NOT NULL
);



-- INSERT INTO profile.users (username, password, email, roles)
-- VALUES ('root', '$2a$10$7wAKq2n7op0LJbscR62nCOMN02FyTakykIVOC0quV6MncqpaBRGJC', 'root@example.com', '{ROLE_ADMIN}');




