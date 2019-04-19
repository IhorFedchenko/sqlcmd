package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.comand.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.io.IOException;

public class MainController {
    private Command[] commands;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view),
                new Help(view),
                new List(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }

    public void run() {
        connectToDb();
        while (true) {
            view.write("Enter a command or help");
            try {
                String input = view.read();
                for (Command command : commands){
                    if (command.canProcess(input)){
                        command.process(input);
                    }
                }
            } catch (IOException e) {
                printError(e);
            }

        }
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

