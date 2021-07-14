package utils;

import connections.DBConnection;
import connections.QueryFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Environment {

    private boolean peopleCreated;
    private boolean connected;
    private ArrayList<String> people;
    private DBConnection connection;
    Scanner sc = new Scanner(System.in);

    public Environment() {
        peopleCreated = false;
        connected = false;
        people = new ArrayList<>();
    }

    public void makeAction() throws StopException, SQLException {
        Printer.printMenu();

        switch (ActionType.from(sc.nextLine())) {
            case CONNECT_DATABASE:
                Printer.printSqlTypes();
                this.connect(DatabaseType.from(sc.nextLine()));
                break;
            case FREE_QUERY:
                new QueryFactory().executeQuery(this.connection);
                break;
            case LIST_INSERT:
                break;
            // TODO: add support for bulk insert
            case BULK_INSERT:
                break;
            case PRINT_TO_CSV:
                new QueryFactory().printToCsv(this.connection);
                break;
            case PRINT_TO_CONSOLE:
                new QueryFactory().printToConsole(this.connection);
                break;
            // TODO: make a way to generate data for any table format
            case CREATE_DATA:
                break;
            case EXIT:
                throw new StopException("Exiting...");
        }
    }

    private void connect (DatabaseType database) {

        String jdbcURL;
        String username;
        String password;
        switch (database) {
            case MS_SQL:
                // jdbc:sqlserver://localhost;user=sa;password=topPassword1;databaseName=BikeStores
                System.out.print("URL of the Database > ");
                jdbcURL = sc.nextLine();
                connection = new DBConnection(jdbcURL);
                this.connected = true;
                break;

            case POSTGRESQL:
                // jdbc:postgresql://localhost:5432/dummyDatabase
                System.out.print("URL of the Database > ");
                jdbcURL = sc.nextLine();
                // user - postgres
                System.out.print("username > ");
                username = sc.nextLine();
                // password - password
                System.out.print("password > ");
                password = sc.nextLine();
                connection = new DBConnection(jdbcURL, username, password);
                this.connected = true;
                break;
        }
    }

}
