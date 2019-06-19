package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Help;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class HelpTest {

    private View view;
    private Command command;

    @Before
    public void setup(){
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void test_can_process_help_string(){
//        given
//        when
        boolean canProcess = command.canProcess("help");
//        then
        assertTrue(canProcess);
    }

    @Test
    public void test_can_process_help_with_param_string(){
//        given
//        when
        boolean canProcess = command.canProcess("help|anyparam");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void test_can_process_nohelp_string(){
//        given
//        when
        boolean canProcess = command.canProcess("nohelp");
//        then
        assertFalse(canProcess);
    }
}
