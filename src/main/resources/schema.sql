drop table if exists user_role cascade;
drop table if exists roles cascade;
drop table if exists users cascade;
drop table if exists classes cascade;
drop table if exists bonuses cascade;
drop table if exists attendances cascade;
drop table if exists attend cascade;
drop table if exists participate_in cascade;
drop table if exists check_attendance cascade;

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
    user_id bigint
);

create table users
(
    id           bigint primary key auto_increment,
    user_name    varchar(255),
    password     varchar(255),
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
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

CREATE TABLE classes (
                         class_id bigint primary key auto_increment,
                         created_at datetime(6) DEFAULT NULL,
                         teacher_id bigint DEFAULT NULL,
                         code varchar(255) DEFAULT NULL,
                         title varchar(255) DEFAULT NULL
);
alter table classes add foreign key (teacher_id) references users(id);

CREATE TABLE bonuses (
                         bonus_id bigint primary key auto_increment,
                         class_id bigint DEFAULT NULL,
                         teacher_id bigint DEFAULT NULL,
                         title varchar(255) DEFAULT NULL,
                         type enum('MCQ','ShortAnswer','LongAnswer') DEFAULT NULL,
                         point_val int DEFAULT NULL,
                         correct_ans varchar(255) DEFAULT NULL,
                         due_date datetime(6) DEFAULT NULL
);
alter table bonuses add foreign key (teacher_id) references users(id);
alter table bonuses add foreign key (class_id) references classes(class_id);



CREATE TABLE attendances (
                               attendance_id bigint primary key auto_increment,
                               class_id bigint DEFAULT NULL,
                               teacher_id bigint DEFAULT NULL,
                               title varchar(255) DEFAULT NULL,
                               due_date datetime(6) DEFAULT NULL
);
alter table attendances add foreign key (teacher_id) references users(id);
alter table attendances add foreign key (class_id) references classes(class_id);

CREATE TABLE attend (
                        attends_id bigint primary key auto_increment,
                        class_id bigint DEFAULT NULL,
                        student_id bigint DEFAULT NULL,
                        attendance int DEFAULT NULL,
                        score int DEFAULT NULL
);
alter table attend add foreign key (class_id) references classes(class_id);
alter table attend add foreign key (student_id) references users(id);


CREATE TABLE check_attendance (
                                    check_id bigint primary key auto_increment,
                                    attendance_id bigint DEFAULT NULL,
                                    checked_in_at datetime(6) DEFAULT NULL,
                                    student_id bigint DEFAULT NULL
);
alter table check_attendance add foreign key (attendance_id) references attendances(attendance_id);

alter table check_attendance add foreign key (student_id) references users(id);



CREATE TABLE participate_in (
                                  participation_id bigint primary key auto_increment,
                                  bonus_id bigint DEFAULT NULL,
                                  student_id bigint DEFAULT NULL,
                                  answer varchar(255) DEFAULT NULL
);
alter table participate_in add foreign key (bonus_id) references bonuses(bonus_id);
alter table participate_in add foreign key (student_id) references users(id);

