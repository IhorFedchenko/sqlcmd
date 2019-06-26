package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Invalid Command. type help for help");
        }
        try {
            manager.clear(data[1]);
            view.write(String.format("Table %s has been cleared successfully", data[1]));
        } catch (SQLException e) {
           view.write(e.getMessage());
           e.printStackTrace();
        }
    }
}
