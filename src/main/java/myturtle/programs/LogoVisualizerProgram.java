package myturtle.programs;

import java.util.List;
import myturtle.display.DisplayPackage;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.programs.program_objects.LogoTurtle;

/**
 * A program that allows users to draw on a canvas using a programmable Turtle.
 *
 * @author William Convertino
 */
public class LogoVisualizerProgram extends Program {

  /**
   * Generates the Logo Visualizer program with a given list of arguments.
   *
   * @param args the list of arguments
   */
  public LogoVisualizerProgram(List<String> args)  {
    super(args);
    this.name = "LogoVisualizer";
    initializeProgramElements();
    initializeDisplayPackage();
  }

  //Initializes the Turtle.
  private void initializeProgramElements() {
    this.myTurtle = new LogoTurtle();
  }

  //Creates a display package with all the programs elements.
  private void initializeDisplayPackage() {
    this.myDisplayPackage = new DisplayPackage();
    myDisplayPackage.add(myTurtle.getDisplayShape());
    this.updateDisplay = true;
  }

  /**
   * Updates the program each game loop.
   *
   * @throws InvalidArgumentException if a read file contains an invalid command.
   */
  @Override
  public void update() throws Exception {
    executeCommand(readNextInstruction());
  }


}
