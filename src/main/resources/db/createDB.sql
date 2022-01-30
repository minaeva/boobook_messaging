CREATE DATABASE bubuk_messages ENCODING 'UTF-8';
CREATE USER admin with password 'admin';
GRANT ALL PRIVILEGES ON DATABASE bubuk_messages TO admin;

drop table if exists message;

create table message
(
    id         serial PRIMARY KEY,
    from_id    bigint       not null,
    to_id      bigint       not null,
    date_added timestamp,
    message    varchar(500) not null
);


INSERT INTO message(from_id, to_id, date_added, message)
VALUES (1, 2, current_timestamp, 'message 1'),
       (2, 1, current_timestamp, 'message 2'),
       (1, 2, current_timestamp, 'message 3'),
       (1, 3, current_timestamp, 'message 4'),
       (4, 2, current_timestamp, 'message 5'),
       (4, 1, current_timestamp, 'message 6');
