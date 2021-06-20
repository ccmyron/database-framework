import com.github.javafaker.Faker;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        JDBCConnection jdbc = new JDBCConnection("jdbc:postgresql://localhost:5432/dummyDatabase",
                                                "postgres", "password");

//        Faker faker = new Faker(); // fake name generator instance
//        ArrayList<String> people = new ArrayList<>();
//
//        for (int i = 0; i < 10; ++i) {
//            String firstName = faker.name().firstName();
//            String lastName = faker.name().lastName();
//            String streetAddress = faker.address().streetAddress();
//            people.add(firstName + "," + lastName + "," + streetAddress);
//        }

        jdbc.insertDataFromSource("test", "test.csv");
    }

}
