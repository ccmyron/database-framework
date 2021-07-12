package utils;

import connections.DBConnection;

import java.util.ArrayList;
import java.util.Scanner;

public class Environment {

    private boolean peopleCreated;
    private ArrayList<String> people;

    public Environment() {
        peopleCreated = false;
        people = new ArrayList<>();
    }

    public void makeAction() throws StopException {
        Scanner sc = new Scanner(System.in);
        printMenu();
        switch (ActionType.from(sc.nextLine())) {
            case CONNECT_DATABASE:
                System.out.print("URL of the Database > "); // jdbc:postgresql://localhost:5432/dummyDatabase
                String jdbcURL = sc.nextLine();
                System.out.print("username > "); // postgres
                String username = sc.nextLine();
                System.out.print("password > "); // password
                String password = sc.nextLine();
                DBConnection connection = new DBConnection(jdbcURL, username, password);
            case CREATE_DATA:

        }
    }

    private static void printMenu() {
        System.out.println("Choose an option");
        System.out.println("connect to db: Create a connection instance");
        System.out.println("create list: Create a list of random people");
        System.out.println("insert list: Populate the database from the list");
        System.out.println("insert file: Populate the database from a file");
        System.out.println("output file: Output the database contents to a csv file");
        System.out.println("exit: Exit");
        System.out.print("> ");
    }
}
