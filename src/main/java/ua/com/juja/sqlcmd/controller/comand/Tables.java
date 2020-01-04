package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        Set<String> tableNames;
        try {
            tableNames = manager.getTableNames();
        } catch (SQLException e) {
            e.printStackTrace();
            view.write(e.getMessage());
            tableNames = new HashSet<>();
        }
        String message = tableNames.toString();
        view.write(message);
    }
}
