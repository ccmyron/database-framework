package connections;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class QueryFactory {

    Scanner sc = new Scanner(System.in);
    Logger log = LogManager.getLogger(QueryFactory.class);

    /**
     *  Execute a free query. Used for CREATE, UPDATE and DELETE
     *
     * @param conn database connection instance
     */

    public void executeQuery(DBConnection conn) {
        System.out.println("Type the query: > ");
        String query = sc.nextLine();

        try {
            Statement statement = conn.getConnection()
                                      .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int rows = statement.executeUpdate(query);
            log.info("{} rows updated", rows);
            statement.close();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }


    /**
     *  Execute a query and print the response to a csv file.
     *  Used for READ.
     *
     * @param conn database connection instance
     */

    public void printToCsv(DBConnection conn) {

        System.out.print("Type the query: > ");
        String query = sc.nextLine();

        try {
            Statement statement = conn.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet queryResult = statement.executeQuery(query);
            ResultSetMetaData rsMetaData = queryResult.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            /* get the column names (headers) */
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; ++i) {
                columnNames[i - 1] = rsMetaData.getColumnName(i);
            }

            List<String[]> csvContent = new ArrayList<>();
            csvContent.add(columnNames);

            /* get every row using column names and write them to csvContent */
            while (queryResult.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    row[i] = queryResult.getString(columnNames[i]);
                }
                csvContent.add(row);
            }

            /* Write the contents to the csv file */
            String csvPath = "output_csv/query_output_" +
                    new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) +
                    ".csv";
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath), ',',
                                                            CSVWriter.NO_QUOTE_CHARACTER,
                                                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                                            CSVWriter.DEFAULT_LINE_END))
            {
                writer.writeAll(csvContent);
                log.info("Query result successfully dumped to {}", csvPath);
            } catch (IOException io) {
                log.error("Error: ", io);
            }
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }


    /**
     *  Execute a query and print the response to the console.
     *  Used for READ.
     *
     * @param conn database connection instance
     */

    public void printToConsole(DBConnection conn) {
        System.out.print("Type the query: > ");
        String query = sc.nextLine();

        try {
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

            queryResult.close();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }


    /**
     *  Execute a bulk insert query.
     *  Used for CREATE and UPDATE.
     *
     * @param conn database connection instance
     */

    public void bulkInsert(DBConnection conn) {
        System.out.print("Type in the table name: > ");
        String tableName = sc.nextLine();
        System.out.print("Type in the path to the csv file > ");
        String csvPath = sc.nextLine();

         try {
             Statement statement = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             int queryResult = statement.executeUpdate(
                     "BULK INSERT " + tableName +
                             " FROM " + csvPath +
                             "WITH\n" +
                             "(\n" +
                             "FIRSTROW = 1,\n" +
                             "FIELDTERMINATOR = ',',\n" +
                             "ROWTERMINATOR = '\\n'\n" +
                             ")"
             );
             log.info("{} rows were inserted into {}", queryResult, tableName);
         } catch (Exception e) {
             log.error("Error: ", e);
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
        log.info("{} rows were inserted", rowsCount);
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
