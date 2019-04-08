package ua.com.juja.sqlcmd.view;

import java.io.IOException;

public interface View {

    void write(String message);

    String read() throws IOException;
}
