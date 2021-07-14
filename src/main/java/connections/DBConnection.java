package connections;

import java.sql.*;


public class DBConnection {

    private final String url;
    private String username;
    private String password;
    private Connection connection;

    public DBConnection(String url) {
        this.url = url;
        try {
            this.connection = DriverManager.getConnection(this.url);
            System.out.println("Successfully connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DBConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println("Successfully connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
