import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection con = ConnectionManager.getConnection();

        DatabaseMetaData dbmd = con.getMetaData();
        String dbName = dbmd.getDatabaseProductName();

        String dbVersion = dbmd.getDatabaseProductVersion();
        String dbUrl = dbmd.getURL();
        String userName = dbmd.getUserName();
        String driverName = dbmd.getDriverName();
        System.out.println("Database Name is " + dbName);
        System.out.println("Database Version is " + dbVersion);
        System.out.println("Database Connection Url is " + dbUrl);
        System.out.println("Database User Name is " + userName);
        System.out.println("Database Driver Name is " + driverName);
        con.close();
    }
}
