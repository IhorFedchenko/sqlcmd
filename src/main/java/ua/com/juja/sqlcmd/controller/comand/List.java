package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;

public class List implements Command {

    private DatabaseManager manager;
    private View view;

    public List(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        String[] tableNames;
        try {
            tableNames = manager.getTableNames();
        } catch (SQLException e) {
            e.printStackTrace();
            view.write(e.getMessage());
            tableNames = new String[0];
        }
        String message = Arrays.toString(tableNames);
            view.write(message);
    }
}
