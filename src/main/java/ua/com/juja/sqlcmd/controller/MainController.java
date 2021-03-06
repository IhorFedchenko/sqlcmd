package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.comand.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.io.IOException;


public class MainController {
    private Command[] commands;
    private View view;


    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new Tables(manager, view),
                new Clear(manager,view),
                new Create(manager, view),
                new Find(manager, view),
                new Unsupported(view),
                new IsConnected(manager, view),
                new Unsupported(view)

        };

    }

    public void run() {
        try {
            doWork();
        } catch (CustomExitException e) {
            // do nothing
        }
    }

    private void doWork() {
        view.write("Hello user!");
        view.write("Please enter the database name, username and password " +
                "in the format connect|database|userName|password");

        while (true) {

            try {
                String input = view.read();
                for (Command command : commands) {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            } catch (Exception e) {
                if (e instanceof CustomExitException){
                    try {
                        throw e;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                printError(e);
            }
            view.write("Enter a command or help");
        }
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

