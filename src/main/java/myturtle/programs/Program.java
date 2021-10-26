package myturtle.programs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import myturtle.commands.Command;
import myturtle.commands.CommandReader;
import myturtle.display.DisplayPackage;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.io.FileReader;
import myturtle.programs.program_objects.Turtle;

/**
 * An abstract class from which each program must be built. It contains
 * all the necessary functionality of a program, including generating IO
 * and Display packages and updating each game loop. Also includes functionality
 * for generating instructions from a text file.
 *
 * @author William Convertino
 */
public abstract class Program extends CommandReader {

  /** A list of all the valid commands for this program. **/
  protected Set<String> programCommands =
      new HashSet<String>(Arrays.asList(new String[]
          {"run"}));

  protected Turtle myTurtle;

  //The name of the program.
  protected String name;

  //An array of the program's arguments
  protected List<String> args;

  //An arraylist with each instruction.
  protected List<Command> instructionList;

  //The current instruction.
  protected int instructionIndex;

  //The IO Elements of the program.
  protected List<Node> myIOPackage;

  //The Display elements of the program.
  protected DisplayPackage myDisplayPackage;

  //Signals whether a new display element has been added.
  protected boolean updateDisplay;

  public Program (List<String> args) {
    this.args = args;
    this.instructionIndex = 0;
  }

  /**
   * Returns an IOPackage that contains all the buttons,
   * mouse handlers, and key listeners that the program will need.
   *
   * @return an IOPackage of all the used IO elements.
   */
  public List<Node> generateIOPackage() {
    return myIOPackage;
  }

  /**
   * Returns a DisplayPackage that contains all the
   * display elements that the program will need.
   *
   * @return a DisplayPackage with all the used JavaFX elements.
   */
  public DisplayPackage getDisplayPackage() {
    if (updateDisplay) {
      updateDisplay = false;
      return myDisplayPackage;
    } else {
      return null;
    }
  }

  /**
   * Adds a node to the program's display package and signals the engine to add new elements
   * to the display.
   *
   * @param n the element to add.
   */
  public void updateDisplayPackage(Node n) {
    if (n == null) {
      return;
    }
    myDisplayPackage.addFirst(n);
    updateDisplay = true;
  }

  /**
   * Adds a list of node to the program's display package and signals the engine to add new elements
   * to the display.
   *
   * @param nodeList a list of the elements to add.
   */
  public void updateDisplayPackage(List<Node> nodeList) {
    if (nodeList == null || nodeList.size() == 0) {
      return;
    }
    for (Node n: nodeList) {
      if (n != null) {
        myDisplayPackage.addFirst(n);
      }
    }
    updateDisplay = true;
  }

  /**
   * Takes in an Object and adds all elements associated with that object to
   * the display.
   *
   * @param o the object to display.
   */
  public void updateDisplayPackage(Object o) {
    if (o instanceof List) {
      updateDisplayPackage((ArrayList)o);
    } else {
      updateDisplayPackage((Node)o);
    }
  }

  public String getName() {
    return name;
  }

  /**
   * Loads a file into the program's instruction list.
   *
   * @param filename the name of the file to read as instructions.
   */
  public void loadFileAsInstructions(String filename) throws IOException {
    this.instructionIndex = 0;
    try {
      FileReader myFileReader = new FileReader(filename);
      this.instructionList = myFileReader.getCommandList();
    } catch (FileNotFoundException e) {
      throw new myturtle.error_handling.FileNotFoundException(filename);
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   *  Returns the next instruction in the instruction list.
   *
   * @return the next instruction in the instruction list.
   */
  protected Command readNextInstruction() {
    if (instructionList == null || instructionIndex>=instructionList.size()) {
      instructionList = null;
      return null;
    }
    Command next = instructionList.get(instructionIndex);
    instructionIndex ++;
    return(next);
  }

  /**
   * Executes a passed command.
   *
   * @param myCommand the command to execute.
   * @throws InvalidArgumentException if the arguments passed are invalid for the given command.
   */
  public Object executeCommand(Command myCommand) throws Exception {
    if (myCommand == null) {
      return null;
    }

    try {
      if (runProgramCommands(myCommand) == false) {
        runTurtleCommands(myCommand);
      }
    } catch ( Exception e) {
      throw e;
    }
    return null;
  }

  //Checks if the passed command is to be handled by the program, rather than the turtle.
  protected boolean runProgramCommands(Command myCommand) throws Exception {
    if (programCommands.contains(myCommand.getCommand())) {
      super.executeCommand(myCommand);
      return true;
    }
    return false;
  }

  private void runTurtleCommands(Command myCommand) throws Exception {
    Object myObject = myTurtle.executeCommand(myCommand);
    if (myObject instanceof ArrayList) {
      updateDisplayPackage((ArrayList)myObject);
    } else {
      updateDisplayPackage((Shape)myObject);
    }
  }

  //Runs the file with the given filename.
  public Object run(String filename) throws IOException {
    try {
      loadFileAsInstructions(filename);
    } catch (myturtle.error_handling.FileNotFoundException e) {
      throw new myturtle.error_handling.FileNotFoundException(filename);
    }
    return null;
  }

  /**
   * Where the logic of the program should be updated. Each call signals
   * one "tick" of a game loop.
   */
  public abstract void update() throws Exception;

}
