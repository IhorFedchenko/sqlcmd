package ua.com.juja.sqlcmd.controller.comand;

public interface Command {

    boolean canProcess(String command);

    void process(String command);
}
