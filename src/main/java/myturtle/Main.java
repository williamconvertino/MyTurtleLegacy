package myturtle;

import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.Dimension;

/**
 *  Initializes and runs a new MyTurtle application.
 *
 * @author William Convertino
 */
public class Main extends Application {

  /**The current version of the application.**/
  public static final String CURRENT_VERSION = "1.0.0";

  public static final String ENGLISH = "English";
  public static final String SPANISH = "Spanish";

  /**The title of the application.**/
  public static final String TITLE = "MyTurtle";

  /**The default size of the window.**/
  public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);

  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start (Stage stage) {
    stage.setTitle(TITLE);
    new MyTurtleEngine(ENGLISH, stage, DEFAULT_SIZE);
  }
}
