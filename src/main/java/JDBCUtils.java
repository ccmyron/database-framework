import com.github.javafaker.Faker;

import java.util.ArrayList;

public class JDBCUtils {

    public static ArrayList<String> generateNames(int n) {

        Faker faker = new Faker(); // fake name generator instance
        ArrayList<String> people = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String streetAddress = faker.address().streetAddress();
            people.add(firstName + "," + lastName + "," + streetAddress);
        }

        return people;
    }

    public static void printMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Output the database contents to a csv file");
        System.out.println("2. Create a list of random people");
        System.out.println("3. Populate the database from the list");
        System.out.println("4. Populate the database from a file");
        System.out.print("> ");
    }

}
