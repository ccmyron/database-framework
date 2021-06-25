import connections.DBConnection;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<String> people = new ArrayList<>();
        boolean peopleCreated = false;

        System.out.print("URL of the Database > "); // jdbc:postgresql://localhost:5432/dummyDatabase
        String jdbcURL = sc.nextLine();
        System.out.print("username > "); // postgres
        String username = sc.nextLine();
        System.out.print("password > "); // password
        String password = sc.nextLine();
        DBConnection jdbc = new DBConnection(jdbcURL, username, password);

        while (true) {
            JDBCUtils.printMenu();
            switch (Integer.parseInt(sc.nextLine())) {
                default -> System.out.println("Wrong input!");
                case 1 -> {
                    System.out.println("Type in the name of the table");
                    String tableName = sc.nextLine();
                    System.out.println("Type in the path to the csv file");
                    String csvPath = sc.nextLine();
                    jdbc.dumpDataToCsv (tableName, csvPath);
                }
                case 2 -> {
                    System.out.println("How many people to generate?");
                    int numOfPeople = Integer.parseInt(sc.nextLine());
                    people = JDBCUtils.generateNames(numOfPeople);
                    peopleCreated = true;
                }
                case 3 -> {
                    System.out.println("Type in the name of the table");
                    String tableName = sc.nextLine();
                    if (peopleCreated) {
                        jdbc.insertData(tableName, people);
                    } else {
                        System.out.println("People names were not generated, generate them and try again!");
                    }
                }
                case 4 -> {
                    System.out.println("Type in the name of the table");
                    String tableName = sc.nextLine();
                    System.out.println("Type in the path to the csv file");
                    String csvPath = sc.nextLine();
                    jdbc.insertDataFromCsv(tableName, csvPath);
                }
                case 5 -> System.exit(0);
            }
        }
    }
}
