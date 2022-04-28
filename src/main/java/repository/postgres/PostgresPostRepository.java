package repository.postgres;

import model.Label;
import model.Post;
import repository.LabelRepository;
import repository.PostRepository;
import until.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresPostRepository implements PostRepository {
    private final static PostRepository INSTANCE = new PostgresPostRepository();
    private final static LabelRepository labelRepository = PostgresLabelRepository.getInstance();

    private PostgresPostRepository() {
    }

    private static final String FIND_BY_ID =
            "SELECT * FROM post WHERE id = ?";

    private static final String FIND_ALL =
            "SELECT * FROM post";

    private static final String SAVE_POST =
            "INSERT INTO post(title, content, created, updated) " +
                    "VALUES (?, ?, now(), null)";

    private static final String MATCH_POST_WITH_LABEL =
            "INSERT INTO label_post(label_id, post_id) VALUES(%d, %d)";

    private static final String MATCH_POST_WITH_WRITER =
            "INSERT INTO writer_post(writer_id, post_id) VALUES (?, ?)";

    private static final String DELETE_POST_BY_ID =
            "DELETE FROM post WHERE id = ?";

    private static final String UPDATE =
            "UPDATE post SET title = ?, content = ?, updated = now() WHERE id = ?";

    private static final String FIND_POSTS_BY_WRITER_ID =
            "SELECT p.id, p.title, p.content, p.created, p.updated, w.id, w.first_name, w.last_name\n" +
                    "FROM post p\n" +
                    "         JOIN writer_post wp\n" +
                    "              ON post_id = p.id\n" +
                    "         JOIN writer w ON wp.writer_id = w.id\n" +
                    "WHERE p.id IN (SELECT post_id\n" +
                    "               FROM writer_post\n" +
                    "               WHERE writer_id = ?)";

    @Override
    public Post getById(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            resultSet.next();
            return buildPost(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post save(Post post) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_POST, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1,post.getTitle());
            prepareStatement.setString(2,post.getContent());
            prepareStatement.executeUpdate();
            var resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            post.setId(resultSet.getObject("id", Integer.class));
            post.setCreated(resultSet.getObject("created", Timestamp.class).toLocalDateTime());
            matchPostWithLabels(post.getId(), post.getLabels());
            return post;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post update(Post post) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, post.getTitle());
            prepareStatement.setString(2, post.getContent());
            prepareStatement.setInt(3, post.getId());
            prepareStatement.executeUpdate();
            var resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            matchPostWithLabels(resultSet.getInt("id"), post.getLabels());
            return buildPost(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = prepareStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while(resultSet.next()) {
                posts.add(buildPost(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getPostsByWriterId(Integer writerId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_POSTS_BY_WRITER_ID)) {
            prepareStatement.setInt(1, writerId);
            var resultSet = prepareStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while(resultSet.next()) {
                posts.add(buildPost(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_POST_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
            prepareStatement.getGeneratedKeys();
            return true;
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    private Post buildPost(ResultSet resultSet) {
        try {
            LocalDateTime updated = resultSet.getTimestamp("updated") == null
                    ? null : resultSet.getObject("updated", Timestamp.class).toLocalDateTime();
            return new Post(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("title", String.class),
                    resultSet.getObject("content", String.class),
                    resultSet.getObject("created", Timestamp.class).toLocalDateTime(),
                    updated,
                    labelRepository.getLabelsByPostId(resultSet.getInt("id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void matchPostWithWriter(Integer postId, Integer writerId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(MATCH_POST_WITH_WRITER)) {
            prepareStatement.setInt(1, writerId);
            prepareStatement.setInt(2, postId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void matchPostWithLabels(Integer postId, List<Label> labels) {
        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            for (Label label : labels) {
                statement.executeUpdate(String.format(MATCH_POST_WITH_LABEL, label.getId(), postId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PostRepository getInstance() {
        return INSTANCE;
    }

}
