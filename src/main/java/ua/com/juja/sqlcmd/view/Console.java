package ua.com.juja.sqlcmd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console implements View {

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read()  {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
