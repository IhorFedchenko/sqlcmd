package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;

public interface DatabaseManager {

    DataSet[] getTableData(String tableName) throws SQLException;

    String[] getTableNames() throws SQLException;

    void connect(String database, String userName, String password);

    void clear(String tableName) throws SQLException;

    void  create(String tableName, DataSet input) throws SQLException;

    void  update(String tableName, int id, DataSet newValue) throws SQLException;

    String[] getTableColumns(String tableName) throws SQLException;

    boolean isConnected();
}
