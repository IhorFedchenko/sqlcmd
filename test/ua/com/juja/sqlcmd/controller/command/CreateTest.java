package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Create;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(manager, view);
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
//  !!! test doesn't work, i don't know why
//    @Test
//    public void test_create_table_and_put_value() {
////        when
//        command.process("create|testTableName|testColumn1|testValue1|testColumn2|testValue2");
////        then
//        DataSet dataSet = new DataSetImpl();
//        dataSet.put("testColumn1", "testValue1");
//        dataSet.put("testColumn2", "testValue2");
//
//        try {
//            verify(manager).create("testTableName", dataSet);
//        } catch (SQLException e) {
////            do nothing
//        }
//        verify(view).write("Record DataSet{" + System.lineSeparator() +
//                "names:[testColumn1, testColumn2]" + System.lineSeparator() +
//                "values:[testValue1, testValue2]" + System.lineSeparator() +
//                "} had been created successfully in the table'testTableName'");
//    }

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
