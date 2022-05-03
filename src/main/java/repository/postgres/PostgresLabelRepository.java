package repository.postgres;

import dto.LabelDto;
import liquibase.pro.packaged.I;
import liquibase.pro.packaged.L;
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
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            resultSet.next();
            return new Label(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Label save(Label label) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1,label.getName());
            prepareStatement.executeUpdate();
            var resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            label.setId(resultSet.getObject("id", Integer.class));
            return label;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Label update(Label label) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE)) {
            prepareStatement.setString(1, label.getName());
            prepareStatement.setInt(2, label.getId());
            prepareStatement.executeUpdate();
            return label;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Label> getAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = prepareStatement.executeQuery();
            List<Label> labels = new ArrayList<>();
            while(resultSet.next())
                labels.add(buildLabel(resultSet));
            return labels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_BY_ID)) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void matchLabelWithPost(Label label, Integer postId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(MATCH_LABEL_WITH_POST)) {
            prepareStatement.setInt(1, label.getId());
            prepareStatement.setInt(2, postId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Label> getLabelsByPostId(Integer postId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_POST_ID)) {
            prepareStatement.setInt(1, postId);
            var resultSet = prepareStatement.executeQuery();
            List<Label> labels = new ArrayList<>();
            while (resultSet.next()) {
                labels.add(buildLabel(resultSet));
            }
            return labels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
