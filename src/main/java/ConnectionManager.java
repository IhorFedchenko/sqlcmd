import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class ConnectionManager {
    private static String urlstring = "jdbc:postgresql://localhost:5432/anytest";
    private static String username = "postgres";
    private static String password = "post";
    private static Connection con;

    public static Connection getConnection() {

        try {
            con = DriverManager.getConnection(urlstring, username, password);
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
        }
        return con;
    }
}
