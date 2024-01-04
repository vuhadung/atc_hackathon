create table if not exists roles
(
    id          bigint primary key auto_increment,
    description varchar(255),
    name        varchar(255)
);

create table if not exists users
(
    id           bigint primary key auto_increment,
    user_name    varchar(255),
    full_name    varchar(255),
    email    varchar(255),
    phone_number    varchar(255),
    hashed_password     varchar(255),
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table if not exists user_role
(
    id      bigint primary key auto_increment,
    role_id bigint,
    user_id bigint
);
alter table user_role add foreign key (role_id) references roles(id);
alter table user_role add foreign key (user_id) references users(id);

CREATE TABLE IF NOT EXISTS classes (
                         class_id bigint primary key auto_increment,
                         teacher_id bigint DEFAULT NULL,
                         title varchar(255) DEFAULT NULL,
                         code varchar(10) DEFAULT NULL,
                         status enum('active','inactive') DEFAULT NULL,
                         created_at datetime(6) DEFAULT NULL
);
alter table classes add foreign key (teacher_id) references users(id);

CREATE TABLE IF NOT EXISTS attendances (
                               attendance_id bigint primary key auto_increment,
                               class_id bigint DEFAULT NULL,
                               teacher_id bigint DEFAULT NULL,
                               datetime datetime(6) DEFAULT NULL
);
alter table attendances add foreign key (teacher_id) references users(id);
alter table attendances add foreign key (class_id) references classes(class_id);

CREATE TABLE IF NOT EXISTS student_class (
                        student_class_id bigint primary key auto_increment,
                        class_id bigint DEFAULT NULL,
                        student_id bigint DEFAULT NULL,
                        attendance int DEFAULT NULL,
                        score int DEFAULT NULL
);
alter table student_class add foreign key (class_id) references classes(class_id);
alter table student_class add foreign key (student_id) references users(id);

CREATE TABLE IF NOT EXISTS student_attendance (
                                    student_attendance_id bigint primary key auto_increment,
                                    attendance_id bigint DEFAULT NULL,
                                    student_id bigint DEFAULT NULL,
                                    is_present tinyint DEFAULT 0
);
alter table student_attendance add foreign key (attendance_id) references attendances(attendance_id);
alter table student_attendance add foreign key (student_id) references users(id);

-- Initial data
DELETE FROM user_role;
DELETE FROM roles;
DELETE FROM users;

INSERT INTO roles (description, name)
VALUES ('Admin role', 'ADMIN');
INSERT INTO roles (description, name)
VALUES ('Teacher role', 'TEACHER');
INSERT INTO roles (description, name)
VALUES ('Student role', 'STUDENT');

INSERT INTO users (user_name, hashed_password, created_at, updated_at)
VALUES ('admin',
        '{bcrypt}$2a$10$FTbr4wRHRKwPzH3NuTMYZ.mEJKX5TKbVi77p/nApp/l408mIcLMd2',
        current_timestamp, current_timestamp);
INSERT INTO user_role (role_id, user_id)
VALUES ((SELECT id FROM roles WHERE name = 'ADMIN'),
        (SELECT id FROM users WHERE user_name = 'admin'));

