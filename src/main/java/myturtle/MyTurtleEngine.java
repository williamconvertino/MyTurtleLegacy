package myturtle;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import myturtle.commands.Command;
import myturtle.display.Display;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.error_handling.InvalidCommandException;
import myturtle.io.IOHandler;
import myturtle.programs.LSystemVisualizerProgram;
import myturtle.programs.LogoVisualizerProgram;
import myturtle.programs.Program;

/**
 * A class to bring together the display and logic of the application,
 * as well as manage programs based on user input in the command line.
 *
 * @author William Convertino
 */
public class MyTurtleEngine {

  public static final String EXIT = "exit";
  public static final String LOGO = "logo";
  public static final String VISUALIZER = "visualizer";
  public static final String VISUALIZER_ALTERNATE = "lsv";
  public static final String[] AVAILABLE_PROGRAMS = {LOGO, VISUALIZER, VISUALIZER_ALTERNATE};

  //Resource packs
  private static final String DEFAULT_RESOURCE_PACKAGE = "myturtle.resources.";

  //Application Variables
  private Timeline myTimeline;

  //Display and IO
  private Display myDisplay;
  private IOHandler myIOHandler;

  private static final double FRAME_DURATION = 1/60.0;

  //Programs
  private Program activeProgram;
  private HashSet initCommands;

  /**
   * Constructs a new MyTurtle engine.
   *
   * @param language the language of the program.
   */
  public MyTurtleEngine(String language, Stage stage, Dimension myDimension) {
    initializeDisplay(stage, myDimension, language);
    initializeHandlers();
    initializePrograms();
    initializeTimeline();
  }

  //Initializes the JavaFX timeline.
  private void initializeTimeline() {
    myTimeline = new Timeline();
    myTimeline.setCycleCount(Timeline.INDEFINITE);
    myTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(FRAME_DURATION), e -> {update();}));
    myTimeline.play();
  }

  //Initializes the display class using a given stage and screen dimension.
  private void initializeDisplay(Stage stage,Dimension myDimension, String language) {
    this.myDisplay = new Display(stage, myDimension, language);
  }

  //Initializes each non-program handler.
  private void initializeHandlers() {
    this.myIOHandler = new IOHandler(myDisplay);
  }

  //Creates a list of the possible program initialization commands.
  private void initializePrograms() {
    initCommands = new HashSet(Arrays.asList(AVAILABLE_PROGRAMS));
  }

  //Attempts to read the input of the command line. Returns a command
  //data-type if a valid command is entered, and null if no command is entered
  //or an invalid command is entered.
  private void tryCommandLine() {
    Command myCommand = new Command(myIOHandler.getActiveCommand());
    if (myCommand.getCommand() == null) {
      return;
    }
    if (activeProgram == null || myCommand.getCommand().equals(EXIT)) {
      callCommand(myCommand);
    } else {
      try {
        activeProgram.executeCommand(myCommand);
      } catch (InvalidCommandException e) {
        myDisplay.showError(e);
      } catch (InvalidArgumentException e) {
        myDisplay.showError(e);
      } catch (Exception e) {
        myDisplay.showError(e);
      }

    }
  }

  //Given a valid command, executes the function associated with
  //that command.
  private void callCommand(Command command) {
    if (initCommands.contains(command.getCommand())) {
      startNewProgram(command);
    } else if (command.getCommand().equals(EXIT)) {
      if (activeProgram == null) {
        System.out.println("\nNo program running" );
      } else {
        System.out.println("\nExiting "+activeProgram.getName() );
      }
      reset();
    } else {
      myDisplay.showError(new InvalidCommandException(command));
    }

  }

  //Initializes a new program based on the initialization command.
  private void startNewProgram(Command c) {
    reset();
    if (c.getCommand().equals(LOGO)) {
      activeProgram = new LogoVisualizerProgram(c.getArgs());
    } else if (c.getCommand().equals(VISUALIZER) || c.getCommand().equals(VISUALIZER_ALTERNATE)) {
      activeProgram = new LSystemVisualizerProgram((c.getArgs()));
    }

    myIOHandler.loadIOPackage(activeProgram.generateIOPackage());


    System.out.println("\nStarting " + activeProgram.getName());

    if (c.getArgs().size() == 1) {
      try {
        activeProgram.loadFileAsInstructions(c.getArgs().get(0));
      } catch (IOException e) {
        myDisplay.showError(e);
      }
    }
  }

  //Resets the display and IO and removes the current program.
  private void reset() {
    myIOHandler.removeCurrentIOPackage();
    myDisplay.unloadDisplayPackage();
    this.activeProgram = null;
  }

  //Updates the active program if one is loaded.
  private void updateProgram() {
    if (activeProgram == null) {
      return;
    }

    try {
      activeProgram.update();

    } catch (InvalidArgumentException e) {
      myDisplay.showError(e);
    } catch (Exception e) {
      myDisplay.showError(e);
    }
    myDisplay.loadDisplayPackage(activeProgram.getDisplayPackage());
  }

  //Executes the application's logic once per game loop.
  private void update() {
    tryCommandLine();
    updateProgram();
  }
}
