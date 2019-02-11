import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String database, String user, String password) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, user, password);
        } catch (SQLException e) {
            System.out.println(String.format("Can't get connection for database: %s user:%s", database, user));
            e.printStackTrace();
        }
    }

}
