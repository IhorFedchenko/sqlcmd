package ua.com.juja.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.CustomExitException;
import ua.com.juja.sqlcmd.controller.comand.Exit;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.*;

public class ExitTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void test_can_process_exit_string() {
//        given
        Command command = new Exit(view);
//        when
        boolean canProcess = command.canProcess("exit");
//        then
        assertTrue(canProcess);
    }

    @Test
    public void test_can_process_asdf_string() {
//        given
        Command command = new Exit(view);
//        when
        boolean canProcess = command.canProcess("asdf");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void test_process_exit_command_throws_CustomExitException() {
//        given
        Command command = new Exit(view);
//        when
        try {
            command.process("exit");
            fail("Expected CustomExitException");
        } catch (CustomExitException e) {
//            do nothing
        }
        Mockito.verify(view).write("Good bye");
    }
}
