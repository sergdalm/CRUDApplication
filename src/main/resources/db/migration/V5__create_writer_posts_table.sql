CREATE TABLE IF NOT EXISTS writer_posts
(
    writer_id int NOT NULL,
    post_id   int NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES post_repository_mysql.writer (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post_repository_mysql.post (id) ON DELETE CASCADE,
    PRIMARY KEY (writer_id, post_id)
)