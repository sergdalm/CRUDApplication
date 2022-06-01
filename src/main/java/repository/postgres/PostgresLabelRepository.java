package repository.postgres;

import model.Label;
import repository.LabelRepository;
import until.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresLabelRepository implements LabelRepository {
    private final static LabelRepository INSTANCE = new PostgresLabelRepository();

    private PostgresLabelRepository() {
    }

    private static final String FIND_BY_ID =
            "SELECT * FROM label WHERE id = ?";
    private static final String SAVE =
            "INSERT INTO label (name) VALUES (?) ON CONFLICT DO NOTHING";
    private static final String FIND_ALL =
            "SELECT * FROM label";
    private static final String UPDATE =
            "UPDATE label SET name = ? WHERE id = ?";
    private static final String DELETE_BY_ID =
            "DELETE FROM label WHERE id = ?";
    private static final String MATCH_LABEL_WITH_POST =
            "INSERT INTO label_post(label_id, post_id) VALUES(?, ?) ON CONFLICT DO NOTHING";
    private static final String FIND_BY_POST_ID =
            "SELECT *\n" +
                    "FROM label\n" +
                    "JOIN label_post lp on label.id = lp.label_id\n" +
                    "WHERE post_id = ?;";

    @Override
    public Label getById(Integer id) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_BY_ID);
        try {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Label(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public Label save(Label label) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE);
        try {
            preparedStatement.setString(1, label.getName());
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            label.setId(resultSet.getObject("id", Integer.class));
            return label;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public Label update(Label label) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(UPDATE);
        try {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setInt(2, label.getId());
            preparedStatement.executeUpdate();
            return label;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public List<Label> getAll() {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_ALL);
        try {
            var resultSet = preparedStatement.executeQuery();
            List<Label> labels = new ArrayList<>();
            while (resultSet.next()) {
                labels.add(buildLabel(resultSet));
            }
            return labels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(DELETE_BY_ID);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public void matchLabelWithPost(Label label, Integer postId) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(MATCH_LABEL_WITH_POST);
        try {
            preparedStatement.setInt(1, label.getId());
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public List<Label> getLabelsByPostId(Integer postId) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_BY_POST_ID);
        try {
            preparedStatement.setInt(1, postId);
            var resultSet = preparedStatement.executeQuery();
            List<Label> labels = new ArrayList<>();
            while (resultSet.next()) {
                labels.add(buildLabel(resultSet));
            }
            return labels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    private Label buildLabel(ResultSet resultSet) throws SQLException {
        return new Label(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("name", String.class));
    }

    public static LabelRepository getInstance() {
        return INSTANCE;
    }
}
