package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        String database = "anytest";
        String username = "postgres";
        String password = "post";
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(database, username, password);
        con = manager.getConnection();

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

        // CREATE
        manager.connect(database, username, password);
        con = manager.getConnection();
        stmt = con.createStatement();
        sql = "INSERT INTO users (name, email) VALUES('John', 'john@gmail.com')";
        stmt.execute(sql);
        con.close();

        // READE
        manager.connect(database, username, password);
        con = manager.getConnection();
        stmt = con.createStatement();
        sql = "SELECT * FROM users";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            System.out.print("ID: " +id);
            System.out.print(", name: " +name);
            System.out.println(", email: " +email);
        }
        rs.close();
        con.close();

        //UPDATE
        manager.connect(database, username, password);
        con = manager.getConnection();
        stmt = con.createStatement();
        sql = "UPDATE users SET email = 'john_update@gmail.com' WHERE name = 'John'";
        stmt.executeUpdate(sql);
        con.close();

        //DELETE
        manager.connect(database, username, password);
        con = manager.getConnection();
        stmt = con.createStatement();
        sql = "DELETE FROM users WHERE MOD (id, 2) = 0";
        stmt.executeUpdate(sql);
        con.close();
    }
}
