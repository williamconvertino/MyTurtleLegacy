package myturtle.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import myturtle.commands.Command;
import myturtle.display.DisplayPackage;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.programs.program_objects.DrawingTurtle;
import myturtle.programs.program_objects.LSystemTurtle;

/**
 * A program that allows users to draw on a canvas using a programmable Turtle.
 *
 * @author William Convertino
 * @author Luke Josephy
 */
public class LSystemVisualizerProgram extends Program {

  /**A map that contains the built-in library of the LSV**/
  public static Map<String, List<Command>> BUIT_IN_LIBRARY = new HashMap<>() {{

    put("F", new ArrayList<Command>() {{add(new Command("pd"));add(new Command("fd"));}} );
    put("G", new ArrayList<Command>() {{add(new Command("pu"));add(new Command("fd"));}} );
    put("A", new ArrayList<Command>() {{add(new Command("pu"));add(new Command("bk"));}} );
    put("B", new ArrayList<Command>() {{add(new Command("pd"));add(new Command("bk"));}} );
    put("+", new ArrayList<Command>() {{add(new Command("rt"));}});
    put("-", new ArrayList<Command>() {{add(new Command("lt"));}});
    put("X", new ArrayList<Command>() {{add(new Command("stamp"));}});

  }};

  //A map of all the symbols and their associated command sequences.
  private Map<String, List<Command>> mySymbolLibrary;

  //A map of all the symbols and their pattern rules
  private Map<String, String> myRuleLibrary;

  //Stores the current fractal algorithm.
  private String currentPattern;

  //The current depth of the project.
  private int myDepth;

  //Signals whether the fractal is ready to execute.
  private boolean readyToExecute;

  private Slider lengthSlider;
  private Slider angleSlider;
  private Slider levelSlider;

  public LSystemVisualizerProgram (List<String> args) {
    super(args);
    this.name = "LSystemVisualizer";
    this.myDepth = 1;
    this.readyToExecute = false;
    this.programCommands.addAll(new ArrayList<>() {{add("start"); add("rule"); add("set");}});
    resetLibraries();
    initializeProgramElements();
    initializeDisplayPackage();
  }

  //Initializes the LSystemTurtle.
  private void initializeProgramElements() {
    this.myTurtle = new LSystemTurtle();
  }

  /**
   * Reverts the symbol library back to the default elements and clears
   * the rules library.
   */
  public void resetLibraries() {
    this.mySymbolLibrary = new HashMap<>();
    this.mySymbolLibrary.putAll(BUIT_IN_LIBRARY);
    this.myRuleLibrary = new HashMap<>();
  }

  //Creates a display package with all the programs elements.
  private void initializeDisplayPackage() {
    this.myDisplayPackage = new DisplayPackage();
    myDisplayPackage.add(myTurtle.getDisplayShape());
    this.updateDisplay = true;
  }

  @Override
  /**
   * Returns an IOPackage that contains all the buttons,
   * mouse handlers, and key listeners that the program will need.
   *
   * @return an IOPackage of all the used IO elements.
   */
  public List<Node> generateIOPackage() {
    this.myIOPackage = new ArrayList<>();
    generateSliders();
    return this.myIOPackage;
  }

  //Initializes the sliders for length, angle, and level.
  private void generateSliders() {
    lengthSlider = initializeSlider("length", 5, 50, 5, 10, 10);
    angleSlider = initializeSlider("angle", -90, 90, 10, 20, 40);
    levelSlider = initializeSlider("level", 0, 10, 1, 1, 1);
  }

  //Creates a new slider with the given conditions.
  private Slider initializeSlider(String label, int startVal, int endVal, int tickVal, int majorTickVal, int blockVal) {
    Label lengthLabel = new Label(label);
    this.myIOPackage.add(lengthLabel);
    Slider mySlider = new Slider(startVal, endVal, tickVal);
    mySlider.setShowTickMarks(true);
    mySlider.setShowTickLabels(true);
    mySlider.setMajorTickUnit(majorTickVal);
    mySlider.setBlockIncrement(blockVal);
    this.myIOPackage.add(mySlider);
    return mySlider;
  }

  //Updates the values in the slider.
  private void checkSliders() {
    try {
      if (lengthSlider.isValueChanging()) {
        myTurtle.executeCommand(new Command("setlength " + (int)lengthSlider.getValue()));
      }
      if (angleSlider.isValueChanging()) {
        executeCommand(new Command("setangle " + (int)angleSlider.getValue()));
      }
      if (levelSlider.isValueChanging()) {
        this.myDepth = (int)levelSlider.getValue();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Adds the given symbol to the symbol library corresponding to a given sequence
   *  of commands.
   *
   * @param symbol the symbol associated with the sequence.
   * @param sequence the sequence of turtle commands to execute.
   * @return null.
   */
  public Object set(String symbol, String sequence) {
    List<Command> commandList = new ArrayList<>();
    generateCommandListFromString(commandList,new ArrayList<>(Arrays.asList(sequence.split(" "))),
        DrawingTurtle.DEFAULT_DRAWING_COMMANDS);
    mySymbolLibrary.put(symbol, commandList);
    return null;
  }

  /**
   *  Sets the initial symbol in the pattern.
   *
   * @return null.
   */
  public Object start(String symbol) {
    this.currentPattern = symbol;
    return null;
  }

  //Updates the pattern.
  public void updatePattern() {
    String newPattern = "";
    for (String s: currentPattern.split("")) {
      if (myRuleLibrary.containsKey(s)) {
        newPattern += myRuleLibrary.get(s);
      } else {
        newPattern += s;
      }
    }
    currentPattern = newPattern;
  }

  //Draws teh final patten.
  private void drawPattern(String pattern) throws Exception {
    for (String s: pattern.split("")) {
      updateDisplayPackage(myTurtle.executeCommand(mySymbolLibrary.get(s)));
    }
  }

  /**
   * Adds a rule to the current rule library associated with the given symbol.
   *
   * @param symbol the symbol associated with the given pattern.
   * @param pattern the fractal pattern to update on each level.
   * @return null.
   */
  public Object rule(String symbol, String pattern) {
    this.myRuleLibrary.put(symbol, pattern);
    return null;
  }

  //Runs the visualizer program.
  private void runVisualization() throws Exception {
    if (this.instructionList == null)  {
      return;
    }
    if (readyToExecute) {
      for (int i = 0; i < myDepth; i++) {
        updatePattern();
      }
      drawPattern(currentPattern);
      readyToExecute = false;
    } else if (instructionIndex < instructionList.size()) {
      for (int i = 0; i < instructionList.size(); i++) {
        executeCommand(readNextInstruction());
        }
      readyToExecute = true;
    }

  }

  /**
   * Updates the program each game loop.
   *
   * @throws InvalidArgumentException if a read file contains an invalid command.
   */
  @Override
  public void update() throws Exception {
    checkSliders();
    try {
      runVisualization();
    } catch (Exception e) {
      this.instructionIndex = instructionList.size();
      throw e;
    }
  }
}
