DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2023-10-15 10:30:00', 'Завтрак', 500, 100000),
       ('2023-10-15 14:30:00', 'Обед', 1000, 100000),
       ('2023-10-15 19:00:00', 'Ужин', 500, 100000),
       ('2023-10-17 00:00:00', 'Еда на граничное значение', 500, 100000),
       ('2023-10-17 12:30:00', 'Обед', 800, 100000),
       ('2023-10-17 18:00:00', 'Ужин', 1000, 100000),
       ('2023-10-18 10:00:00', 'Завтрак', 500, 100001),
       ('2023-10-18 13:00:00', 'Обед', 700, 100001),
       ('2023-10-18 19:00:00', 'Ужин', 1200, 100001),
       ('2023-10-20 10:10:00', 'Завтрак', 500, 100001),
       ('2023-10-20 14:00:00', 'Обед', 500, 100001),
       ('2023-10-20 18:00:00', 'Ужин', 500, 100001)