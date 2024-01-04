drop table if exists student_class cascade;
drop table if exists student_attendance cascade;
drop table if exists user_role cascade;
drop table if exists attendances cascade;
drop table if exists classes cascade;
drop table if exists roles cascade;
drop table if exists users cascade;

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
                         created_at datetime(6) DEFAULT NULL,
                         student_count int DEFAULT 0
);
alter table classes add foreign key (teacher_id) references users(id);

CREATE TABLE IF NOT EXISTS attendances (
                               attendance_id bigint primary key auto_increment,
                               class_id bigint DEFAULT NULL,
                               datetime datetime(6) DEFAULT NULL
);
alter table attendances add foreign key (teacher_id) references users(id);
alter table attendances add foreign key (class_id) references classes(class_id);

CREATE TABLE IF NOT EXISTS student_class (
                        student_class_id bigint primary key auto_increment,
                        class_id bigint DEFAULT NULL,
                        student_id bigint DEFAULT NULL,
                        attendance int DEFAULT 0
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
INSERT INTO roles(description, name)
SELECT * FROM (SELECT 'Admin Role', 'ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'ADMIN'
) LIMIT 1;

INSERT INTO roles(description, name)
SELECT * FROM (SELECT 'Teacher Role', 'TEACHER') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'TEACHER'
) LIMIT 1;

INSERT INTO roles(description, name)
SELECT * FROM (SELECT 'Student Role', 'STUDENT') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'STUDENT'
) LIMIT 1;
