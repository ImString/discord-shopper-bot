package me.imstring.discordshopper.database.providers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@RequiredArgsConstructor
public class MySQL implements Database {

    @NonNull
    private final String host;
    private final int port;
    @NonNull
    private final String database;
    @NonNull
    private final String username;
    @NonNull
    private final String password;

    private Connection connection;

    private String buildJdbcUrl() {
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", host, port, database);
    }

    /**
     * Open the connection.
     *
     * @throws SQLException on failure
     */
    public void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Failed to find class 'com.mysql.cj.jdbc.Driver'", e);
            }
            String url = buildJdbcUrl();
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);

            connection = DriverManager.getConnection(url, props);
        }
    }

    /**
     * Close the connection.
     *
     * @throws SQLException on failure
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }

    /**
     * Executes an update.
     *
     * @param command the command to be executed
     * @param args    the command arguments
     * @throws SQLException on failure
     */
    public void update(String command, Object... args) throws SQLException {
        openConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to execute update statement: '" + command + "'", e);
        }
    }

    /**
     * Executes a query.
     *
     * @param command the command to be executed
     * @param args    the command arguments
     * @return returns an instance of {@link me.imstring.discordshopper.database.Database.Query}
     * @throws SQLException on failure
     */
    public Query query(String command, Object... args) throws SQLException {
        openConnection();
        return new Query(connection, command, args);
    }
}