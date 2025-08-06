package me.imstring.discordshopper.database.providers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
public class HikariMySQL implements Database {

    @NonNull
    private final String host;
    private final int port;
    @NonNull
    private final String database;
    @NonNull
    private final String username;
    @NonNull
    private final String password;

    private HikariDataSource dataSource;

    private HikariConfig buildConfig() {
        String jdbcUrl = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                host, port, database
        );
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        return config;
    }

    /**
     * Open the connection.
     *
     * @throws SQLException on failure
     */
    public synchronized void openConnection() {
        if (dataSource == null) {
            HikariConfig config = buildConfig();
            dataSource = new HikariDataSource(config);
        }
    }

    /**
     * Close the connection.
     *
     * @throws SQLException on failure
     */
    public synchronized void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(command)) {

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
        Connection conn = dataSource.getConnection();
        return new Query(conn, command, args);
    }
}