CREATE SCHEMA IF NOT EXISTS profile;
CREATE SCHEMA IF NOT EXISTS device;


DO $$ BEGIN
    CREATE TYPE profile.ROLE AS ENUM ('ROLE_ADMIN', 'ROLE_USER');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;


CREATE TABLE IF NOT EXISTS profile.users
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


CREATE TABLE IF NOT EXISTS device.firmware_timestamp
(
    id               SERIAL PRIMARY KEY,
    user_id          INTEGER REFERENCES profile.users (id) ON DELETE CASCADE,
    unixtime         BIGINT       NOT NULL,
    device_model     VARCHAR(255) NOT NULL,
    device_serial    VARCHAR(255) NOT NULL,
    firmware_version VARCHAR(255) NOT NULL
);




