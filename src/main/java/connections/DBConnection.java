package connections;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;

public class DBConnection {

    private final String jdbcURL;
    private final String username;
    private final String password;
    private Connection connection;

    public DBConnection(String jdbcURL, String username, String password) {
        this.jdbcURL = jdbcURL;
        this.username = username;
        this.password = password;

        try {
            this.connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            System.out.println("Successfully connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
