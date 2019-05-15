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

        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\tдля подключения к базе данных, с которой будем работать");

        view.write("\tlist");
        view.write("\t\tprint all tables of the current database");

        view.write("\tclear|tableName");
        view.write("\t\tдля очистки всей таблицы"); // TODO а если юзер случайно ввел команду? Может переспросить его?

        view.write("\tfind|tableName");
        view.write("\t\tget table contents 'tableName'");

        view.write("\thelp");
        view.write("\t\tprint help information");


        view.write("\texit");
        view.write("\t\texit sqlcmd");
    }
}
