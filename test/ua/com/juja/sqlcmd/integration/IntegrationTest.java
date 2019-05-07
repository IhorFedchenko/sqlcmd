package ua.com.juja.sqlcmd.integration;


import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;


    @BeforeClass
    public static void setup() {
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

    public String getData() {

        try {
            String result = new String(out.toByteArray(), "UTF-8");
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
