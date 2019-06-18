package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Connect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void test_can_process_with_parameters_string(){
//        given
//        when
        boolean canProcess = command.canProcess("connect|anyparameters");
//        then
        assertTrue(canProcess);
    }

    @Test
    public void test_can_process_without_parameters_string(){
//        given
//        when
        boolean canProcess = command.canProcess("connect");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void test_connect_to_db(){
//        when
        command.process("connect|anytest|postgres|post");
//        then
        verify(manager).connect("anytest", "postgres", "post");
        verify(view).write("Successful");
    }

    @Test
    public void test_validation_error_when_count_parameters_is_less_then_is_necessary(){
        try{
            command.process("connect|anytest");
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException e){
            assertEquals("Incorrect number of parameters separated by '|', expected 4, but there are: 2", e.getMessage());
        }
    }

    @Test
    public void test_validation_error_when_count_parameters_is_more_then_is_necessary(){
        try{
            command.process("connect|anytest|postgres|post|post");
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException e){
            assertEquals("Incorrect number of parameters separated by '|', expected 4, but there are: 5", e.getMessage());
        }
    }
}
