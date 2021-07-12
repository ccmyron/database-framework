import connections.DBConnection;
import testData.DataGenerator;
import utils.ActionType;
import utils.Environment;
import utils.StopException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

// TODO: Separate the cases into functions; Clean up this file

public class Main {

    public static void main(String[] args) throws StopException, SQLException {
        Environment environment = new Environment();

        while (true) {
            try {
                environment.makeAction();
            } catch (StopException | SQLException e) {
                break;
            }
        }

        while (true) {
            case 1 -> {
                System.out.println("Type in the name of the table");
                String tableName = sc.nextLine();
                System.out.println("Type in the path to the csv file");
                String csvPath = sc.nextLine();
                jdbc.dumpDataToCsv(tableName, csvPath);
            }
            case 2 -> {
                System.out.println("How many people to generate?");
                int numOfPeople = Integer.parseInt(sc.nextLine());
                people = DataGenerator.generateNames(numOfPeople);
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
        }
    }
}
