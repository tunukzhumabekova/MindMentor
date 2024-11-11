-- Drop types if they exist
DROP TYPE IF EXISTS role CASCADE;
DROP TYPE IF EXISTS status CASCADE;

-- Drop sequences if they exist
DROP SEQUENCE IF EXISTS users_info_seq CASCADE;
DROP SEQUENCE IF EXISTS users_seq CASCADE;
DROP SEQUENCE IF EXISTS courses_id_seq CASCADE;
DROP SEQUENCE IF EXISTS direction_id_seq CASCADE;
DROP SEQUENCE IF EXISTS feedback_id_seq CASCADE;
DROP SEQUENCE IF EXISTS mentor_info_id_seq CASCADE;
DROP SEQUENCE IF EXISTS price_of_service_id_seq CASCADE;
DROP SEQUENCE IF EXISTS project_id_seq CASCADE;
DROP SEQUENCE IF EXISTS service_id_seq CASCADE;
DROP SEQUENCE IF EXISTS users_seq CASCADE;

-- Drop tables if they exist
DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS quantity CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS price_of_service CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS mentor_info CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_info CASCADE;
DROP TABLE IF EXISTS direction CASCADE;

-- ENUM
CREATE TYPE role AS ENUM ('ADMIN', 'MENTOR', 'USER');
CREATE TYPE status AS ENUM ('BLOCKED', 'UNBLOCKED');

-- Create Tables
CREATE TABLE users_info (
                            id SERIAL PRIMARY KEY,
                            email VARCHAR UNIQUE NOT NULL,
                            password VARCHAR NOT NULL,
                            role role
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       fio VARCHAR NOT NULL,
                       image VARCHAR,
                       date_of_registration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       status status,
                       phone_number VARCHAR UNIQUE,
                       users_info_id INTEGER REFERENCES users_info (id)
);

CREATE TABLE direction (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE mentor_info (
                             id SERIAL PRIMARY KEY,
                             direction_id INTEGER REFERENCES direction(id),
                             language VARCHAR,
                             experience INTEGER,
                             about_mentor VARCHAR,
                             video_url VARCHAR,
                             users_id INTEGER REFERENCES users(id)
);

CREATE TABLE service (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR NOT NULL UNIQUE,
                         description VARCHAR,
                         users_id INTEGER REFERENCES users(id)
);

CREATE TABLE price_of_service (
                                  id SERIAL PRIMARY KEY,
                                  price DOUBLE PRECISION,
                                  mentor_id INTEGER REFERENCES mentor_info(id),
                                  service_id INTEGER REFERENCES service(id)
);

CREATE TABLE project (
                         id SERIAL PRIMARY KEY,
                         video_url VARCHAR,
                         description VARCHAR,
                         price DOUBLE PRECISION
);

CREATE TABLE quantity (
                          id SERIAL PRIMARY KEY,
                          quantity_of_mentors INTEGER,
                          direction_id INTEGER REFERENCES direction (id),
                          language VARCHAR,
                          project_id INTEGER REFERENCES project (id)
);

CREATE TABLE feedback (
                          id SERIAL PRIMARY KEY,
                          rating DOUBLE PRECISION,
                          description VARCHAR,
                          date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          users_id INTEGER REFERENCES users(id),
                          mentor_info_id INTEGER REFERENCES mentor_info(id)
);

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         course_name VARCHAR NOT NULL,
                         description VARCHAR,
                         file_url VARCHAR,
                         price DOUBLE PRECISION,
                         language VARCHAR NOT NULL,
                         what_you_will_learn VARCHAR,
                         last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         mentor_info_id INTEGER REFERENCES mentor_info(id),
                         direction_id INTEGER REFERENCES direction(id)
);

CREATE TABLE user_courses (
                              id SERIAL PRIMARY KEY,
                              users_info_id INTEGER REFERENCES users(id),
                              courses_id INTEGER REFERENCES courses(id)
);


ALTER TABLE user_courses
    ADD CONSTRAINT fk_user_courses_users_info_id FOREIGN KEY (users_info_id) REFERENCES users_info(id),
    ADD CONSTRAINT fk_user_courses_courses_id FOREIGN KEY (courses_id) REFERENCES courses(id);

ALTER TABLE users
    ADD CONSTRAINT fk_users_users_info_id FOREIGN KEY (users_info_id) REFERENCES users_info (id);

ALTER TABLE mentor_info
    ADD CONSTRAINT fk_mentor_info_direction_id FOREIGN KEY (direction_id) REFERENCES direction (id),
  ADD CONSTRAINT fk_mentor_info_users_id FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE service
    ADD CONSTRAINT fk_service_users_id FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE price_of_service
    ADD CONSTRAINT fk_price_of_service_mentor_id FOREIGN KEY (mentor_id) REFERENCES mentor_info (id),
  ADD CONSTRAINT fk_price_of_service_service_id FOREIGN KEY (service_id) REFERENCES service (id);

ALTER TABLE quantity
    ADD CONSTRAINT fk_quantity_direction_id FOREIGN KEY (direction_id) REFERENCES direction (id),
  ADD CONSTRAINT fk_quantity_project_id FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE feedback
    ADD CONSTRAINT fk_feedback_users_id FOREIGN KEY (users_id) REFERENCES users (id),
  ADD CONSTRAINT fk_feedback_mentor_info_id FOREIGN KEY (mentor_info_id) REFERENCES mentor_info (id);

ALTER TABLE courses
  ADD CONSTRAINT fk_courses_mentor_info_id FOREIGN KEY (mentor_info_id) REFERENCES mentor_info (id),
  ADD CONSTRAINT fk_courses_direction_id FOREIGN KEY (direction_id) REFERENCES direction (id);