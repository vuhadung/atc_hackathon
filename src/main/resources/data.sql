insert into users (id, user_name, full_name, email, phone_number, hashed_password, created_at, updated_at) values (1, 'fseebright0', 'Fredric Seebright', 'fseebright0@prlog.org', '603-354-8822', '$2a$04$qGcPxvLgc/AVLgB1i/tyVuPKD7OEfMIb56xGCRU7MZZ8rDLRMRzq.', '2023-10-03 08:38:27', '2023-09-06 15:57:54');
insert into users (id, user_name, full_name, email, phone_number, hashed_password, created_at, updated_at) values (2, 'mllewhellin1', 'Mariette Llewhellin', 'mllewhellin1@studiopress.com', '336-754-7640', '$2a$04$QOUHB5BvTbX.Gz1SCZ1P.eQHU3BXkVdMxydVtjh7.i50L923dPn3K', '2023-10-06 22:03:00', '2023-10-16 06:31:27');
insert into users (id, user_name, full_name, email, phone_number, hashed_password, created_at, updated_at) values (3, 'rmoorerud2', 'Ryan Moorerud', 'rmoorerud2@tripadvisor.com', '998-947-8032', '$2a$04$J5xvFpNtNmnxIouoggtDC.lgVL3Ik2OD6y0.mJlaZBg/TeSfkWGNK', '2023-07-13 19:08:40', '2023-08-11 14:02:59');

insert into user_role (id, role_id, user_id) values (1, 1, 3);
insert into user_role (id, role_id, user_id) values (2, 3, 2);
insert into user_role (id, role_id, user_id) values (3, 2, 2);

insert into classes (class_id, teacher_id, title, code, status, created_at, student_count) values (1, 2, 'Zoolab', 'Uq3dT', 'inactive', '2023-09-16 16:09:43', 42);
insert into classes (class_id, teacher_id, title, code, status, created_at, student_count) values (2, 2, 'Vagram', 'VgUu2', 'active', '2023-03-28 10:23:09', 31);
insert into classes (class_id, teacher_id, title, code, status, created_at, student_count) values (3, 2, 'Span', 'a5VrY', 'inactive', '2023-01-21 00:14:12', 13);
insert into classes (class_id, teacher_id, title, code, status, created_at, student_count) values (4, 2, 'Greenlam', 'zJXRY', 'inactive', '2023-10-18 12:13:40', 44);

