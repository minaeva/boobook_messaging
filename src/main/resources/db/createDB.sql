CREATE DATABASE bubuk_messages ENCODING 'UTF-8';
CREATE USER admin with password 'admin';
GRANT ALL PRIVILEGES ON DATABASE bubuk_messages TO admin;


create table message
(
    id         serial,
    from       bigint       not null,
    to         bigint       not null,
    date_added timestamp,
    message    varchar(500) not null
);

