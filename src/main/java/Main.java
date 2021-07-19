import utils.ArgumentException;
import utils.StopException;
import utils.Environment;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ArgumentException {
        Environment environment = new Environment();
        while (true) {
            try {
                environment.makeAction();
            } catch (StopException stop) {
                break;
            } catch (SQLException | IOException | ArgumentException exception) {
                exception.printStackTrace();
            }
        }
    }
}
