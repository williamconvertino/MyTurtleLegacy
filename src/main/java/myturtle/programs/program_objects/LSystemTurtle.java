package myturtle.programs.program_objects;


import java.util.ArrayList;
import java.util.List;
import myturtle.commands.Command;
import myturtle.error_handling.InvalidArgumentException;

/**
 * An extension of the turtle class that can run L-System Visualizer style commands.
 *
 * @author Luke Josephy
 * @author William Convertino
 */
public class LSystemTurtle extends DrawingTurtle {

  private String length = "30";
  private String turnAngle = "60";

  /**
   * Constructs a new (hidden) Turtle with a default shape pointed right.
   */
  public LSystemTurtle () {
    setDisplayShape();
    this.angle = -90;
    this.penDown = true;
  }

  /**
   * Sets the distance that the turtle will move.
   *
   * @param length the distance that the turtle will move.
   * @return null.
   */
  public Object setlength(String length) {
      this.length = length;
      return null;
  }

  /**
   * Sets the angle that the turtle will turn.
   *
   * @param turnAngle the angle that the turtle will turn.
   * @return null.
   */
  public Object setangle(String turnAngle) {
    this.turnAngle = turnAngle;
    return null;
  }

  public Object fd () {
    return(this.fd(length));
  }
  public Object bk () {
    return(this.bk(length));
  }

  public Object lt () {
    return(this.lt(turnAngle));
  }

  public ArrayList<Object> parseCommand(Command command) throws InvalidArgumentException {
    List<String> args = command.getArgs();
    char[] chars = args.get(0).toCharArray();
    ArrayList<Object> displayElements = new ArrayList<>();
    for (char symbol : chars) {
      if (symbol == 'F') {
        //displayElements.add(super.executeCommand(new Command("fd", new String[]{length})));
      }
      if (symbol == '+') {
        //displayElements.add(super.executeCommand(new Command("rt", new String[]{turnAngle})));
      }
      if (symbol == '-') {
        //displayElements.add(super.executeCommand(new Command("lt", new String[]{turnAngle})));
      }
    }
    return displayElements;
  }

  public Object rt() {
    return (this.rt(turnAngle));
  }
}
