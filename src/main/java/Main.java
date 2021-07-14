import utils.Environment;
import utils.StopException;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws StopException, SQLException {
//
//        String url = "jdbc:sqlserver://localhost;user=sa;password=topPassword1;databaseName=BikeStores";
//        Connection connection = DriverManager.getConnection(url);
//
//        Statement statement = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//        String query = "select top 10 * from [BikeStores].[sales].[customers]";
//        ResultSet result =  statement.executeQuery("select top 10 * from [BikeStores].[sales].[customers]");
//        ResultSetMetaData metadata = result.getMetaData();
//        result.absolute(3);
//        for(int i = 1 ; i <= metadata.getColumnCount(); i++) {
//            System.out.println(result.getString(i));
//        }

        Environment environment = new Environment();
        while (true) {
            try {
                environment.makeAction();
            } catch (StopException | SQLException e) {
                break;
            }
        }
    }
}
