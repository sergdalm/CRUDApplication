package until;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static Connection connection;

    static {
        loadDriver();
    }

    @SneakyThrows
    public static Connection get() {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY)
        );
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        ConnectionManager.connection = get();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;

    }

    public static PreparedStatement getPreparedStatementWithGeneratedKeys(String sql) {
        ConnectionManager.connection = get();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;

    }

    public static Statement getStatement() {
        ConnectionManager.connection = get();
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (!ConnectionManager.connection.isClosed()) {
                ConnectionManager.connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
