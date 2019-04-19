package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Exit;
import ua.com.juja.sqlcmd.controller.comand.Help;
import ua.com.juja.sqlcmd.controller.comand.List;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.io.IOException;
import java.util.Arrays;

public class MainController {
    private Command[] commands;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view), new Help(view), new List(manager, view)};
    }

    public void run() {
        connectToDb();
        while (true) {
            view.write("Enter a command or help");
            try {
                String command = view.read();
                if (commands[2].canProcess(command)) {
                    commands[2].process(command);
                } else if (commands[1].canProcess(command)) {
                    commands[1].process(command);
                } else if (command.startsWith("find|")) {
                    doFind(command);
                } else if (commands[0].canProcess(command)) {
                    commands[0].process(command);
                }
            } catch (IOException e) {
                printError(e);
            }

        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void connectToDb() {
        view.write("Hello user!");
        view.write("Please enter the database name, username and password in the format database|userName|password");

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Incorrect number of parameters separated by '|', 3 expected, but there are: " + data.length);
                }

                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("Successful");
    }

    private void printError(Exception e) {
        String message = /*e.getClass().getSimpleName() + ": " + */ e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + /*cause.getClass().getSimpleName() + ": " + */ cause.getMessage();
        }
        view.write("Failure! because of: " + message);
        view.write("Try again.");
    }
}

