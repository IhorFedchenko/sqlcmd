package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.view.View;

public class Exit implements Command {

    private View view;

    public Exit(View view){
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("Good bye");
        System.exit(0);
    }
}
