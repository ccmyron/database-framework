package connections;

import java.sql.*;


public class DBConnection {

    private final String url;
    private Connection connection;


    /**
     *  Construct connection for MSSQL
     */

    public DBConnection(String url) {
        this.url = url;
        try {
            this.connection = DriverManager.getConnection(this.url);
            System.out.println("Successfully connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Construct connection for postgreSQL
     */

    public DBConnection(String url, String username, String password) {
        this.url = url;

        try {
            this.connection = DriverManager.getConnection(this.url, username, password);
            System.out.println("Successfully connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
