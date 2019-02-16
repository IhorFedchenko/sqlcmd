import java.sql.*;
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

    public String[] getTableNames() {
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
            rs.last();
            String[] tables = new String[rs.getRow()];
            rs.beforeFirst();
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public void clear(String tableName){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE  FROM "+ tableName);
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void create(DataSet input) {
        try {
            Statement stmt = connection.createStatement();
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input,"'%s',");
           String sql = "INSERT INTO public.users (" + tableNames + ")" + "VALUES (" + values + ")";
            stmt.execute(sql);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length()-1);
        return values;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length()-1);
        return string;
    }

    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM "+ tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()){
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++){
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        return size;
    }
}
