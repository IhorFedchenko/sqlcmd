package ua.com.juja.sqlcmd.controller.comand;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Create implements Command {

    private final DatabaseManager manager;
    private final View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0 || data.length == 2){
            throw new IllegalArgumentException("there must be an even number of parameters in the format" +
                    "'create|tableName|column1|value1|column2|value2|...|columnN|valueN',");
        }
        String tableName = data[1];

        DataSet dataSet = new DataSetImpl();
        for (int index = 1; index <(data.length / 2); index++){
            String columnName = data[index*2];
            String value = data[index*2+1];

            dataSet.put(columnName, value);
        }
        try {
            manager.create(tableName, dataSet);
        } catch (SQLException e) {
            e.printStackTrace();
            view.write(e.getMessage());
        }

        view.write(String.format("Record %s had been created successfully in the table'%s'", dataSet, tableName));
    }
}
