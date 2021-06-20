import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class JDBCConnection {

    private final String jdbcURL;
    private final String username;
    private final String password;

    public JDBCConnection (String jdbcURL, String username, String password) {
        this.jdbcURL = jdbcURL;
        this.username = username;
        this.password = password;
    }

    public void insertData (String tableName, ArrayList<String> people) {
        try {
            int rowsCount = 0;
            Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            for (int i = 0; i < people.size(); ++i) {
                String query = "INSERT INTO " + tableName + "(first_name, last_name, address) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                String person = people.get(i);
                String[] tokens = person.split(",");
                statement.setString(1, tokens[0]);
                statement.setString(2, tokens[1]);
                statement.setString(3, tokens[2]);
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("A new row has been inserted");
                    rowsCount++;
                }
            }
            System.out.printf("%d rows were inserted", rowsCount);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDataFromSource (String tableName, String csvPath) {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            int rowsCount = 0;
            List<String[]> csvData = getDataFromCsv(csvPath);
            for (int i = 1; i < csvData.size(); ++i) {
                String query = "INSERT INTO " + tableName + "(first_name, last_name, address) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, csvData.get(i)[1]);
                statement.setString(2, csvData.get(i)[2]);
                statement.setString(3, csvData.get(i)[3]);
                int rows = statement.executeUpdate();
                if (rows > 0) {
//                    System.out.println("A new row has been inserted");
                    rowsCount++;
                }
            }
            System.out.printf("%d rows were inserted", rowsCount);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getDataFromCsv (String csvPath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            return Collections.unmodifiableList(reader.readAll());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dumpDataToCsv (String tableName) {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);

            String query = "SELECT * FROM " + tableName;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData rsMetaData = result.getMetaData();
            int columntCount = rsMetaData.getColumnCount();

            // get the column names (headers)
            String[] columnNames = new String[columntCount];
            for (int i = 1; i <= columntCount; ++i) {
                columnNames[i - 1] = rsMetaData.getColumnName(i);
            }
            List<String[]> csvContent = new ArrayList<>();
            csvContent.add(columnNames);

            // get every row using column names and write them to csvContent
            while (result.next()) {
                String[] row = new String[columntCount];
                for (int i = 1; i <= columntCount; ++i) {
                    row[i - 1] = result.getString(rsMetaData.getColumnName(i));
                }
                csvContent.add(row);
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter("test.csv"))) {
                writer.writeAll(csvContent);
            }
            connection.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
