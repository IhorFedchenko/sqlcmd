package ua.com.juja.sqlcmd.integration;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;


    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testHelp() {
        //given
        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        //when
        //then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Supported commands are:\r\n" +
                        "\tconnect|databaseName|userName|password\r\n"+
                "\t\tдля подключения к базе данных, с которой будем работать\r\n"+
                "\tlist\r\n" +
                "\t\tprint all tables of the current database\r\n" +
                        "\tclear|tableName\r\n"+
                "\t\tдля очистки всей таблицы\r\n"+
                "\tfind|tableName\r\n" +
                "\t\tget table contents 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tprint help information\r\n" +
                "\texit\r\n" +
                "\t\texit sqlcmd\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testUnsupported() {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "You can't use command 'unsupported' while do not connect using command connect|databaseName|userName|password\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "You can't use command 'list' while do not connect using command connect|databaseName|userName|password\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "You can't use command 'find|user' while do not connect using command connect|databaseName|userName|password\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add("connect|anytest|postgres|post");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Successful\r\n" +
                "Enter a command or help\r\n" +
                "Unsupported comand: unsupported\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.add("connect|anytest|postgres|post");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Successful\r\n" +
                "Enter a command or help\r\n" +
                "[users, cars]\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add("connect|anytest|postgres|post");
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Successful\r\n" +
                "Enter a command or help\r\n" +
                "--------------------\r\n" +
                "|id|name|email|\r\n" +
                "--------------------\r\n" +
                "|10|Mark|mark@gmail.com|\r\n" +
                "|11|Luke|luke@gmail.com|\r\n" +
                "--------------------\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.add("connect|anytest|postgres|post");
        in.add("list");
        in.add("connect|other|postgres|post");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Successful\r\n" +
                "Enter a command or help\r\n" +
                "[users, cars]\r\n" +
                "Enter a command or help\r\n" +
                "Successful\r\n" +
                "Enter a command or help\r\n" +
                "[other_table]\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|anytest");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Hello user!\r\n" +
                "Please enter the database name, username and password in the format connect|database|userName|password\r\n" +
                "Failure! because of: Incorrect number of parameters separated by '|', expected 4, but there are: 2\r\n" +
                "Try again.\r\n" +
                "Enter a command or help\r\n" +
                "Good bye\r\n", getData());
    }

    @Test
    public void testFindAfterConnect_withData() {
        // given
        in.add("connect|anytest|postgres|post");
        in.add("clear|users");
        in.add("create|users|id|10|name|Mark|email|mark@gmail.com");
        in.add("create|users|id|11|name|Luke|email|luke@gmail.com");
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

//then
        assertEquals("Hello user!" + System.lineSeparator() +
                "Please enter the database name, username and password in the format connect|database|userName|password" + System.lineSeparator() +
                "Successful" + System.lineSeparator() +
                "Enter a command or help" + System.lineSeparator() +
                "Table users has been cleared successfully" + System.lineSeparator() +
                "Enter a command or help" + System.lineSeparator() +
                "Record DataSet{" + System.lineSeparator() +
                "names:[id, name, email]" + System.lineSeparator() +
                "values:[10, Mark, mark@gmail.com]" + System.lineSeparator()  +
                "} had been created successfully in the table'users'" + System.lineSeparator() +
                "Enter a command or help" + System.lineSeparator() +
                "Record DataSet{" + System.lineSeparator() +
                "names:[id, name, email]" + System.lineSeparator() +
                "values:[11, Luke, luke@gmail.com]" + System.lineSeparator() +
                "} had been created successfully in the table'users'" + System.lineSeparator() +
                "Enter a command or help" + System.lineSeparator() +
                "--------------------" + System.lineSeparator() +
                "|id|name|email|" + System.lineSeparator() +
                "--------------------" + System.lineSeparator() +
                "|10|Mark|mark@gmail.com|" + System.lineSeparator() +
                "|11|Luke|luke@gmail.com|" + System.lineSeparator() +
                "--------------------" + System.lineSeparator() +
                "Enter a command or help" + System.lineSeparator() +
                "Good bye" + System.lineSeparator(), getData());
    }

    public String getData() {

        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
