package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.comand.Clear;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void test_clear_table() {
//        given

//        when
        command.process("clear|users");
//        then
        verify(manager).clear("users");
        verify(view).write("Table users has been cleared successfully");
    }

    @Test
    public void test_can_process_clear_with_parameters_string() {
        // when
        boolean canProcess = command.canProcess("clear|users");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void test_cant_process_clear_without_parameters_string() {
//        when
        boolean canProcess = command.canProcess("clear");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void test_validation_error_when_count_parameters_is_less_than2() {
        // when
        try {
            command.process("clear");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Invalid Command. type help for help", e.getMessage());
        }
    }

    @Test
    public void test_validation_error_when_count_parameters_is_more_than2() {
        // when
        try {
            command.process("clear|table|qwe");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Invalid Command. type help for help", e.getMessage());
        }
    }
}
