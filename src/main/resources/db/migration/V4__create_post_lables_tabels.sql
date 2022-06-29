CREATE TABLE IF NOT EXISTS post_labels
(
    label_id int NOT NULL,
    post_id  int NOT NULL,
    FOREIGN KEY (label_id) REFERENCES post_repository_mysql.label (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post_repository_mysql.post (id) ON DELETE CASCADE,
    primary key (label_id, post_id)
)