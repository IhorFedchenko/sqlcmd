package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.view.View;


public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        //TODO translate to english
        view.write("Supported commands are:");

        view.write("\tlist");
        view.write("\t\tprint all tables of the current database");

        view.write("\thelp");
        view.write("\t\tprint help information");

        view.write("\tfind|tableName");
        view.write("\t\tget table contents 'tableName'");

        view.write("\texit");
        view.write("\t\texit sqlcmd");
    }
}
