package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Create;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTest {

    private DatabaseManager manager;
    private View view;
    private Command command;
    private DataSet dataSet;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(manager, view);
        dataSet = new DataSet();
    }

    @Test
    public void test_can_process_with_parameters_string() {
//        given
//        when
        boolean canProcess = command.canProcess("create|anyparameters");
//        then
        assertTrue(canProcess);
    }

    @Test
    public void test_can_process_without_parameters_string() {
//        given
//        when
        boolean canProcess = command.canProcess("create");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void test_create_table_and_put_value() {
//        when
        command.process("create|testTableName|testColumn1|testValue1|testColumn2|testValue2");
//        then

        verify(view).write("Record DataSet{" + System.lineSeparator() +
                "names:[testColumn1, testColumn2]" + System.lineSeparator() +
                "values:[testValue1, testValue2]" + System.lineSeparator() +
                "} had been created successfully in the table'testTableName'");


    }

    @Test
    public void test_validation_error_when_count_parameters_is_incorrect_without_key_and_value() {
        // when
        try {
            command.process("create|testTableName");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
//            then
            assertEquals("there must be an even number of parameters in the format'create|tableName|column1|value1|column2|value2|...|columnN|valueN',", e.getMessage());
        }
    }

    @Test
    public void test_validation_error_when_count_parameters_is_incorrect() {
        // when
        try {
            command.process("create|testTableName|testColumn1|testValue1|testColumn2");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
//            then
            assertEquals("there must be an even number of parameters in the format'create|tableName|column1|value1|column2|value2|...|columnN|valueN',", e.getMessage());
        }
    }

}
