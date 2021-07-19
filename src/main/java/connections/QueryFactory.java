package connections;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QueryFactory {

    Scanner sc = new Scanner(System.in);


    /**
     *  Execute a free query. Used for CREATE, UPDATE and DELETE
     *
     * @param conn database connection instance
     */

    public void executeQuery(DBConnection conn) throws SQLException {
        System.out.print("Type the query: > ");
        String query = sc.nextLine();

        Statement statement = conn.getConnection()
                                  .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int rows = statement.executeUpdate(query);
        System.out.println(rows + " rows updated");
    }


    /**
     *  Execute a query and print the response to a csv file.
     *  Used for READ.
     *
     * @param conn database connection instance
     */

    public void printToCsv(DBConnection conn) throws SQLException {

        System.out.print("Type the query: > ");
        String query = sc.nextLine();
        System.out.print("Type in the path to the csv file > ");
        String csvPath = sc.nextLine();

        Statement statement = conn.getConnection()
                                  .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery(query);
        ResultSetMetaData rsMetaData = result.getMetaData();
        int columnCount = rsMetaData.getColumnCount();

        /* get the column names (headers) */
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; ++i) {
            columnNames[i - 1] = rsMetaData.getColumnName(i);
        }

        List<String[]> csvContent = new ArrayList<>();
        csvContent.add(columnNames);

        /* get every row using column names and write them to csvContent */
        while (result.next()) {
            String[] row = new String[columnCount];
            for (int i = 1; i <= columnCount; ++i) {
                row[i - 1] = result.getString(rsMetaData.getColumnName(i));
            }
            csvContent.add(row);
        }

        /* Write the contents to the csv file */
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
            writer.writeAll(csvContent);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    /**
     *  Execute a query and print the response to the console.
     *  Used for READ.
     *
     * @param conn database connection instance
     */

    public void printToConsole(DBConnection conn) throws SQLException {
        System.out.print("Type the query: > ");
        String query = sc.nextLine();

        Statement statement = conn.getConnection().createStatement(
                                          ResultSet.TYPE_SCROLL_INSENSITIVE,
                                          ResultSet.CONCUR_READ_ONLY);
        ResultSet queryResult = statement.executeQuery(query);
        ResultSetMetaData rsMetaData = queryResult.getMetaData();

        int columnCount = rsMetaData.getColumnCount();

        for (int i = 1; i <= columnCount; ++i) {
            System.out.format("%s ", rsMetaData.getColumnName(i));
        }

        System.out.print("\n");
        while (queryResult.next()) {
            for (int i = 1; i <= columnCount; ++i) {
                System.out.format("%s ", queryResult.getString(i));
            }
            System.out.print("\n");
        }
    }


    /**
     *  Execute a bulk insert query.
     *  Used for CREATE and UPDATE.
     *
     * @param conn database connection instance
     */

    public void bulkInsert(DBConnection conn) throws SQLException {
        System.out.print("Type the table name: > ");
        String tableName = sc.nextLine();
        System.out.print("Type in the path to the csv file > ");
        String csvPath = sc.nextLine();



         try (Statement statement = conn.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
             int queryResult = statement.executeUpdate(
                     "BULK INSERT " + tableName +
                             " FROM " + csvPath +
                             "WITH\n" +
                             "(\n" +
                             "FIRSTROW = 1,\n" +
                             "FIELDTERMINATOR = ',',\n" +
                             "ROWTERMINATOR = '\\n'\n" +
                             ");"
             );
             System.out.println(queryResult + " rows were inserted");
         } catch (SQLException e) {
             e.printStackTrace();
         }

    }

    // TODO: make this function accept different types of tables

    public void insertDataFromList(DBConnection conn,
                                   String tableName,
                                   @NotNull ArrayList<String> people
    ) throws SQLException {

        int rowsCount = 0;
        for (String person : people) {
            String query = "INSERT INTO " + tableName + "(first_name, last_name, address) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.getConnection().prepareStatement(query);
            String[] tokens = person.split(",");
            statement.setString(1, tokens[0]);
            statement.setString(2, tokens[1]);
            statement.setString(3, tokens[2]);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                rowsCount++;
            }
        }
        System.out.printf("%d rows were inserted", rowsCount);
    }

    // TODO: make this function accept different types of tables

    public void insertDataFromCsv(DBConnection conn, String tableName, String csvPath) throws SQLException {

        int rowsCount = 0;
        List<String[]> csvData = getDataFromCsv(csvPath);
        for (int i = 1; i < csvData.size(); ++i) {
            String query = "INSERT INTO " + tableName + "(first_name, last_name, address) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.getConnection().prepareStatement(query);
            statement.setString(1, csvData.get(i)[1]);
            statement.setString(2, csvData.get(i)[2]);
            statement.setString(3, csvData.get(i)[3]);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                rowsCount++;
            }
        }
        System.out.printf("%d rows were inserted", rowsCount);
    }

    /**
     *  Read the contents of a csv file and save them into a List of String arrays.
     *
     * @param csvPath path to the csv file
     * @return List<String[]>
     */

    public List<String[]> getDataFromCsv(String csvPath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            return Collections.unmodifiableList(reader.readAll());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Wrong path!");
    }
}
