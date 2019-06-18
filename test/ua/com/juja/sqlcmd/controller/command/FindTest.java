package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.controller.comand.Command;
import ua.com.juja.sqlcmd.controller.comand.Find;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void test_print_table_data() {
//        given
        when(manager.getTableColumns("users"))
                .thenReturn(new String[]{"id", "name", "email"});

        DataSet user1 = new DataSet();
        user1.put("id", 10);
        user1.put("name", "Mark");
        user1.put("email", "mark@gmail.com");
        DataSet user2 = new DataSet();
        user2.put("id", 11);
        user2.put("name", "Luke");
        user2.put("email", "luke@gmail.com");

        DataSet[] data = new DataSet[]{user1, user2};
        when(manager.getTableData("users")).thenReturn(data);
//        when
        command.process("find|users");
//        then
        shouldPrint("[--------------------, " +
                "|id|name|email|, --------------------, " +
                "|10|Mark|mark@gmail.com|, " +
                "|11|Luke|luke@gmail.com|, " +
                "--------------------]");
    }

    @Test
    public void test_cant_process_find_with_farameters_string() {
//        when
        boolean canProcess = command.canProcess("find|users");
//        then
        assertTrue(canProcess);
    }

    @Test
    public void test_cant_process_asdf_string() {
//        when
        boolean canProcess = command.canProcess("asdf|users");
//        then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
//        given
        when(manager.getTableColumns("users"))
                .thenReturn(new String[]{"id", "name", "email"});

        when(manager.getTableData("users")).thenReturn(new DataSet[0]);
//        when
        command.process("find|users");
//        then
        shouldPrint("[--------------------, |id|name|email|, --------------------, --------------------]");

    }

    @Test
    public void testPrintTableDataWithOneColumn() {
        // given
        when(manager.getTableColumns("test"))
                .thenReturn(new String[]{"id"});

        DataSet user1 = new DataSet();
        user1.put("id", 12);

        DataSet user2 = new DataSet();
        user2.put("id", 13);

        DataSet[] data = new DataSet[]{user1, user2};
        when(manager.getTableData("test")).thenReturn(data);

        // when
        command.process("find|test");

        shouldPrint("[--------------------, |id|, --------------------, |12|, |13|, --------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}