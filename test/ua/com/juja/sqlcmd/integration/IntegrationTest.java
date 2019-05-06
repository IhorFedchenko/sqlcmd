package ua.com.juja.sqlcmd.integration;


import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static LogOutputStream out;


    @BeforeClass
    public static void setup() {
        in = new ConfigurableInputStream();
        out = new LogOutputStream();
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
        assertEquals ("nullHello user!\n" +
                "Please enter the database name, username and passwordin the format connect|database|userName|password\n" +
                "Good bye\n", out.getData());
    }

}
