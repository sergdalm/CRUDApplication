package repository.postgres;

import exceptions.LoginErrorException;
import model.Writer;
import repository.WriterRepository;
import until.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresWriterRepository implements WriterRepository {

    private static final WriterRepository INSTANCE = new PostgresWriterRepository();

    private final static String CREATE_DATABASE =
            "CREATE DATABASE post_repository;";

    private final static String CREATE_TABLE_WRITER =
            "CREATE TABLE writer\n" +
                    "(\n" +
                    "    id         SERIAL PRIMARY KEY,\n" +
                    "    first_name VARCHAR(128) NOT NULL,\n" +
                    "    last_name  VARCHAR(128) NOT NULL,\n" +
                    "    email      VARCHAR(128) NOT NULL UNIQUE,\n" +
                    "    password   VARCHAR(128) NOT NULL\n" +
                    "\n" +
                    ");";
    private final static String CREATE_TABLE_POST =
            "CREATE TABLE post\n" +
                    "(\n" +
                    "    id      SERIAL PRIMARY KEY,\n" +
                    "    title   VARCHAR(220) NOT NULL,\n" +
                    "    content TEXT         NOT NULL,\n" +
                    "    created TIMESTAMP    NOT NULL,\n" +
                    "    updated TIMESTAMP\n" +
                    ");";
    private final static String CREATE_TABLE_WRITER_POST =
            "CREATE TABLE writer_post\n" +
                    "(\n" +
                    "    writer_id INT REFERENCES writer (id) ON DELETE CASCADE ,\n" +
                    "    post_id   INT REFERENCES post (id)  ON DELETE CASCADE UNIQUE\n" +
                    ");";
    private final static String CREATE_TABLE_LABEL =
            "CREATE TABLE label\n" +
                    "(\n" +
                    "    id   SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(128) UNIQUE NOT NULL\n" +
                    ");";
    private final static String CREATE_TABLE_LABEL_POST =
            "CREATE TABLE label_post\n" +
                    "(\n" +
                    "    label_id INT REFERENCES label (id) ON DELETE CASCADE,\n" +
                    "    post_id  INT REFERENCES post (id) NOT NULL ON DELETE CASCADE\n" +
                    ");";
    private final static String FIND_ALL =
            "SELECT * " +
                    "FROM writer";

    private final static String FIND_BY_ID =
            "SELECT * FROM writer WHERE id = ?";

    private final static String FIND_BY_EMAIL =
            "SELECT * FROM writer WHERE email = ?";

    private final static String UPDATE = "";

    private final static String DELETE = "";

    private final static String SAVE =
            "INSERT INTO writer(first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

    private PostgresWriterRepository() {
    }

    static {
        createDatabase();
    }

    @Override
    public Writer getById(Integer id) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_BY_ID);
        try {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildWriter(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public Writer save(Writer writer) {
        createDatabase();
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE);
        try {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setString(3, writer.getEmail());
            preparedStatement.setString(4, writer.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return buildWriter(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public Writer update(Writer obj) {
        return null;
    }

    @Override
    public List<Writer> getAll() {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_ALL);
        try {
            var resultSet = preparedStatement.executeQuery();
            List<Writer> writers = new ArrayList<>();
            while (resultSet.next()) {
                writers.add(buildWriter(resultSet));
            }
            return writers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public Optional<Writer> getWriterByEmail(String email) {
        PreparedStatement preparedStatement = ConnectionManager.getPreparedStatement(FIND_BY_EMAIL);
        try {
            preparedStatement.setString(1, email);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildWriter(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(preparedStatement);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }


    @Override
    public Writer getWriterByName(String firstName, String lastName) {
        return null;
    }

    private Writer buildWriter(ResultSet resultSet) throws SQLException {
        return new Writer(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("first_name", String.class),
                resultSet.getObject("last_name", String.class),
                resultSet.getObject("email", String.class),
                resultSet.getObject("password", String.class),
                null
        );
    }

    private static void createDatabase() {
        Statement statement = ConnectionManager.getStatement();
        try {
            statement.executeUpdate(CREATE_TABLE_WRITER);
            statement.executeUpdate(CREATE_TABLE_POST);
            statement.executeUpdate(CREATE_TABLE_LABEL);
            statement.executeUpdate(CREATE_TABLE_WRITER_POST);
            statement.executeUpdate(CREATE_TABLE_LABEL_POST);

        } catch (SQLException e) {
            // Database has been created
        } finally {
            ConnectionManager.closeConnection(statement);
        }
    }

    public static WriterRepository getInstance() {
        return INSTANCE;
    }

}
