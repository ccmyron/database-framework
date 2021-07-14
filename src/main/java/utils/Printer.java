package utils;

public class Printer {
    public static void printMenu() {
        System.out.println("Choose an option");
        System.out.println("\"connect\"       : Create a connection instance");
        System.out.println("\"query\"         : Execute a query");
        System.out.println("\"insert\"        : Populate the database from the list");
        System.out.println("\"bulk\"          : Populate the database from a file");
        System.out.println("\"print-csv\"     : Output the query result to a csv file");
        System.out.println("\"print-console\" : Output the query result to the console");
        System.out.println("\"list\"          : Output the query result to the console");
        System.out.println("\"exit\"          : Exit");
        System.out.print("> ");
    }

    public static void printSqlTypes() {
        System.out.println("Type in the database type");
        System.out.println("mssql    - Microsoft SQL Server");
        System.out.println("postgres - PostgreSQL");
        System.out.println("mysql    - MySQL");
        System.out.print("> ");
    }
}
