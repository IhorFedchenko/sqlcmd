package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/";
    private Connection connection;

//    public Connection getConnection() {
//        return connection;
//    }

    @Override
    public void connect(String database, String user, String password) {
        try {
            if (connection != null){
                connection.close();
            }
            connection = DriverManager.getConnection(
                    DATABASE_URL + database, user, password);

        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    e.getMessage());
        }
    }

    @Override
    public Set<String> getTableNames() throws SQLException {
        Set<String> tables = new LinkedHashSet<String>();
        try
                (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            rs.last();
            rs.beforeFirst();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        }
    }

    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE  FROM " + tableName);
        }
    }

    @Override
    public void create(String tableName, DataSet input) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");
            String sql = "INSERT INTO public." + tableName + " (" + tableNames + ")" + "VALUES (" + values + ")";
            stmt.execute(sql);
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
//        TODO impl LIMIT and OFFSET to sql query
        int size = getSize(tableName);
        List<DataSet> result = new ArrayList<DataSet>(size);

//        List<DataSet> result = new LinkedList<DataSet>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT  * FROM " + tableName)) {
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    dataSet.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
            }
            return result;
        }
    }

    private int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) throws SQLException {
        String tableNames = getNameFormated(newValue, "%s = ?,");
        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
        Set<String> tables = new LinkedHashSet<String>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'")) {
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }
}
