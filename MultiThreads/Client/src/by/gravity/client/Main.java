package by.gravity.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        ClientApp clientApp = new ClientApp();
        clientApp.showMenu();
    }

}
