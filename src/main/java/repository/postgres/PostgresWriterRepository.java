package repository.postgres;

import exceptions.LoginErrorException;
import liquibase.pro.packaged.S;
import model.Writer;
import repository.WriterRepository;
import until.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresWriterRepository implements WriterRepository {

    private static final WriterRepository INSTANCE = new PostgresWriterRepository();

    private PostgresWriterRepository() {
    }

    private final static String FIND_ALL =
            "SELECT * " +
            "FROM writer";
    private final static String FIND_BY_ID = "";

    private final static String FIND_BY_EMAIL =
            "SELECT * FROM writer WHERE email = ?";

    private final static String UPDATE = "";

    private final static String DELETE = "";

    private final static String SAVE = "";

    @Override
    public Writer getById(Integer id) {
        return null;
    }

    @Override
    public Writer save(Writer obj) {
        return null;
    }

    @Override
    public Writer update(Writer obj) {
        return null;
    }

    @Override
    public List<Writer> getAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = prepareStatement.executeQuery();
            List<Writer> writers = new ArrayList<>();
            while(resultSet.next()) {
                writers.add(buildWriter(resultSet));
            }
            return writers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Writer> getWriterByEmail(String email) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
            prepareStatement.setString(1, email);
            var resultSet = prepareStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(buildWriter(resultSet));
            }
            else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }



    @Override
    public Writer getWriterByName(String firstName, String lastName) {
        return null;
    }

    public static WriterRepository getInstance() {
        return INSTANCE;
    }

}
