package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class Find implements Command {
//TODO formatTableData
    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        Set<String> tableColumns;
        try {
            tableColumns = manager.getTableColumns(tableName);
        } catch (SQLException e) {
            tableColumns = new LinkedHashSet<String>();
            e.printStackTrace();
            view.write(e.getMessage());
        }
        printHeader(tableColumns);

        DataSet[] tableData;
        try {
            tableData = manager.getTableData(tableName);
        } catch (SQLException e) {
            tableData = new DataSet[0];
            e.printStackTrace();
            view.write(e.getMessage());
        }
        printTable(tableData);
    }

    private void printHeader(Set<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
        view.write("--------------------");
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }
}
