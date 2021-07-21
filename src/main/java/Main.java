import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.StopException;
import utils.Environment;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args)  {
        try {
            Environment environment = new Environment();
            while (true) {
                try {
                    environment.makeAction();
                } catch (StopException stop) {
                    break;
                }
            }
        } catch (Exception exception) {
            log.error("Error: ", exception);
        }
    }
}
