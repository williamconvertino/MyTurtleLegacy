package myturtle.io;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import myturtle.display.Display;

/**
 * A handler to deal with all the IO functions of the program, including
 * buttons, keystrokes, and command-line inputs.
 *
 * @author William Convertino
 * @since 0.0.1
 */
public class IOHandler {

  //Command Variables
  /**
   *  The unprocessed command that has been input by the user.
   */
  private String activeCommand;

  /**
   * The TextField where the user inputs their commands.
   */
  private TextField commandLine;

  /**
   * The history of previously executed commands (with the index of each corresponding
   * to the order in which they were called).
   */
  private ArrayList<String> commandHistory;

  //IOPackage Variables
  /**
   *
   */
  private List<Node> currentIOPackage;

  /**
   * The index of the currently selected command in commandHistory.
   */
  private int commandHistoryIndex;

  //Display
  /**
   * The display where each IO device is to be shown.
   */
  private Display myDisplay;

  /**
   * Constructs an IO Handler
   *
   * @param myDisplay the display where the command line and buttons are shown.
   */
  public IOHandler(Display myDisplay) {
    this.myDisplay = myDisplay;
    this.commandLine = new TextField();
    this.commandHistory = new ArrayList<>();
    initialize();
  }

  private void initialize () {
    EventHandler<ActionEvent> getCommand = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        getCommandFromCommandLine();
      }
    };
    commandLine.setOnAction(getCommand);
    myDisplay.addCommandLine(commandLine,getCommand);
  }

  /**
   * Adds a Button to myDisplay.
   *
   * @param b the Button to add to the scene.
   */
  public void addNode(Node b) {
    myDisplay.addNodeToPanel(b);
  }

  /**
   * Removes a Button from myDisplay.
   *
   * @param b the Button to remove.
   */
  public void removeNode(Node b) {
    myDisplay.removeNodeFromNodePanel(b);
  }

  /**
   * Adds a MouseEvent to myDisplay.
   *
   * @param m the MouseEvent to add to the scene.
   */
  public void addMouseEvent(MouseEvent m) {
    //myDisplay.addMouseEvent(m);
  }

  /**
   * Adds a KeyEvent to myDisplay.
   *
   * @param k the KeyEvent to add to the scene.
   */
  public void addKeyEvent(KeyEvent k) {
    //myDisplay.addKeyEvent(k);
  }

  //Retrieves the command from the command line, adds it to the command
  //history, and clears the command line.
  public void getCommandFromCommandLine() {
    if (commandLine.getText().equals("")) {
      return;
    }
    activeCommand = commandLine.getText();
    commandHistory.add(activeCommand);
    commandLine.clear();
  }

  //Loads in the next most recently used command.
  private void loadPreviousCommand () {
    if (!commandHistory.isEmpty()) {
      commandLine.clear();
      commandLine.setText(commandHistory.get(commandHistoryIndex%commandHistory.size()));
      commandHistoryIndex ++;
    }
  }

  //Resets the index of the commandHistory
  private void resetCommandHistoryIndex () {
    commandHistoryIndex = 0;
  }

  /**
   * Returns the currently active command and sets it to null.
   *
   * @return the user's command.
   */
  public String getActiveCommand() {
    String command = activeCommand;
    activeCommand = null;
    return command;
  }

  /**
   * Loads the given IO package and adds its elements to
   * the display.
   *
   * @param p the IOPackage to load.
   */
  public void loadIOPackage(List<Node> p) {
    removeCurrentIOPackage();
    if (p == null) {
      currentIOPackage = new ArrayList<>();
      return;
    }
    currentIOPackage = p;
    for (Node b: currentIOPackage) {
      addNode(b);
    }
  }

  /**
   * Removes every element of the IOPackage from the
   * display.
   */
  public void removeCurrentIOPackage() {
    if (currentIOPackage == null) {
      return;
    }
    for (Node b: currentIOPackage) {
      removeNode(b);
    }
    this.currentIOPackage = null;
  }

  /**
   * Gets the current IOPackage
   */
  public List<Node> getCurrentIOPackage() {
    return currentIOPackage;
  }
  
}
