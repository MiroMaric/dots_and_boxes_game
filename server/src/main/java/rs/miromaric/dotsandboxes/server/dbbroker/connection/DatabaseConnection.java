package rs.miromaric.dotsandboxes.server.dbbroker.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import rs.miromaric.dotsandboxes.server.settings.SettingsLoader;

/**
 *
 * @author miro
 */
//TODO: Connection Pool
public class DatabaseConnection {

    private final Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() throws SQLException, ClassNotFoundException {
        String dbUrl = SettingsLoader.getInstance().getProperty("db.url");
        String dbUser = SettingsLoader.getInstance().getProperty("db.user");
        String dbPassword = SettingsLoader.getInstance().getProperty("db.password");
        System.out.println(dbUrl);
        System.out.println(dbUser);
        System.out.println(dbPassword);
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        connection.setAutoCommit(false);
    }

    public static DatabaseConnection getInstance() {
        try {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
    }

}
