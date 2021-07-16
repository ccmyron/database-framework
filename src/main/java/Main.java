import javafx.scene.paint.Stop;
import utils.ArgumentException;
import utils.Environment;
import utils.StopException;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
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
