CREATE TABLE IF NOT EXISTS post
(
    id      int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title   varchar(128) NOT NULL UNIQUE,
    content text         NOT NULL,
    created timestamp    NOT NULL,
    updated timestamp
);