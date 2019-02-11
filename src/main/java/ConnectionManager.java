import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

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

    public String[] getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
            String[] tables = new String[100]; //TODO remove magic number
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class); //TODO remove this after remove magic number
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
