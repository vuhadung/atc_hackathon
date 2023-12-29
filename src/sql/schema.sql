drop table if exists user_role cascade;
drop table if exists roles cascade;
drop table if exists users cascade;

create table roles
(
    id          int primary key auto_increment,
    description varchar(255),
    name        varchar(255)
);

create table user_role
(
    id      int primary key auto_increment,
    role_id int,
    user_id int
);

create table users
(
    id           int primary key auto_increment,
    user_name    varchar(255),
    password     varchar(255),
    created_date datetime default current_timestamp,
    updated_date datetime default current_timestamp on update current_timestamp
);

alter table user_role add foreign key (role_id) references roles(id);

alter table user_role add foreign key (user_id) references users(id);


-- Initial data
INSERT INTO roles (description, name)
VALUES ('Admin role', 'ADMIN');
INSERT INTO roles (description, name)
VALUES ('Teacher role', 'TEACHER');
INSERT INTO roles (description, name)
VALUES ('Student role', 'STUDENT');


INSERT INTO users (user_name, password, created_date, updated_date)
VALUES ('admin',
        '{bcrypt}$2a$10$FTbr4wRHRKwPzH3NuTMYZ.mEJKX5TKbVi77p/nApp/l408mIcLMd2',
        current_timestamp, current_timestamp);
INSERT INTO user_role (role_id, user_id)
VALUES ((SELECT id FROM roles WHERE name = 'ADMIN'),
        (SELECT id FROM users WHERE user_name = 'admin'));
