CREATE DATABASE bubuk_messages ENCODING 'UTF-8';
CREATE USER admin with password 'admin';
GRANT ALL PRIVILEGES ON DATABASE bubuk_messages TO admin;

drop table if exists message;

create table message
(
    id         serial  PRIMARY KEY,
    from_id       bigint       not null,
    to_id         bigint       not null,
    date_added timestamp,
    message    varchar(500) not null
);

