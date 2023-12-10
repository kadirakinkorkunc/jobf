INSERT INTO member(id, email, password, created_at, updated_at)
values
(1, 'test1@email.com', 'someb2cryptpassword1', now(), now()),
(2, 'test2@email.com', 'someb2cryptpassword2', now(), now()),
(3, 'test3@email.com', 'someb2cryptpassword3', now(), now());
ALTER TABLE member ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM member) + 1;
