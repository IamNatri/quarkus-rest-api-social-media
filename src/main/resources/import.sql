-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

    -- HERe the code to run in postgress

-- CREATE DATABASE "quarkus-social";
--
-- CREATE TABLE users (
--                        id bigserial not null PRIMARY KEY,
--                        username VARCHAR(255) UNIQUE NOT NULL,
--                        age VARCHAR(255) NOT NULL
-- );
--
--
-- CREATE TABLE POSTS (
--                        id bigserial not null PRIMARY KEY,
--                        post_text VARCHAR(150) NOT NULL,
--                        dateTime TIMESTAMP NOT NULL,
--                        user_id bigint NOT NULL REFERENCES users(id)
-- );
--
-- CREATE TABLE FOLLOWERS (
--                            id bigserial not null PRIMARY KEY,
--                            user_id bigint NOT NULL REFERENCES users(id),
--                            follower_id bigint NOT NULL REFERENCES users(id)
-- );