package testData;

import com.github.javafaker.Faker;

import java.util.ArrayList;

public class DataGenerator {

    public static ArrayList<String> generateNames(int number) {
        Faker faker = new Faker(); // fake name generator instance
        ArrayList<String> people = new ArrayList<>();

        for (int i = 0; i < number; ++i) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String streetAddress = faker.address().streetAddress();
            people.add(firstName + "," + lastName + "," + streetAddress);
        }

        return people;
    }

}