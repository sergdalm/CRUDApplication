CREATE DATABASE IF NOT EXISTS post_repository_mysql;

USE post_repository_mysql;

CREATE SCHEMA IF NOT EXISTS post_repository_mysql;

CREATE TABLE IF NOT EXISTS writer
(
    id         int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(128) NOT NULL,
    last_name  varchar(128) NOT NULL,
    email      varchar(128) NOT NULL UNIQUE,
    password   varchar(128) NOT NULL
);