package myturtle.programs.program_objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class DrawingTurtle extends Turtle {

  /** A list of all the valid commands for this turtle. **/
  public static final Set<String> DEFAULT_DRAWING_COMMANDS =
      new HashSet<String>(Arrays.asList(new String[]
          {"fd", "bk", "pu", "pd", "home", "lt", "rt", "ht", "st", "stamp"}));


  //Keeps track of the state of the pen.
  protected boolean penDown;

  /**
   * Constructs a new Turtle with a default shape pointed up.
   */
  public DrawingTurtle () {
    setDisplayShape();
  }

  /**
   *  Moves the turtle forwards the specified number of pixels.
   *
   * @param distance the number of pixels to move.
   * @return null.
   */
  public Object fd(String distance) throws NumberFormatException {
    int distanceToMove = Integer.parseInt(distance);
    double oldX = myDisplayShape.getLayoutX();
    double oldY = myDisplayShape.getLayoutY();
    myDisplayShape.setLayoutX( oldX +  (distanceToMove * Math.cos(Math.toRadians(angle))));
    myDisplayShape.setLayoutY( oldY + (distanceToMove * Math.sin(Math.toRadians(angle))));
    Line myLine = null;
    if (penDown) {
      myLine = new Line();
      myLine.setStartX(oldX);
      myLine.setStartY(oldY);
      myLine.setEndX(myDisplayShape.getLayoutX());
      myLine.setEndY(myDisplayShape.getLayoutY());
    }
    return(myLine);
  }

  /**
   *  Moves the turtle backwards the specified number of pixels.
   *
   * @param distance the number of pixels to move.
   * @return null.
   */
  public Object bk(String distance) throws NumberFormatException {
    return fd(negate(distance));
  }

  //Rotates the turtle by angle given.
  private void rotate(int angle) {
    this.angle += angle;
    Rotate myRotate = new Rotate();
    myRotate.setAngle(angle);
    myDisplayShape.getTransforms().addAll(myRotate);
  }

  /**
   * Turns the Turtle to the left by the specified number of degrees.
   *
   * @param angle the angle at which it should turn.
   * @return null.
   */
  public Object lt(String angle) throws NumberFormatException {
    int angleToRotate = Integer.parseInt(angle, 10);
    rotate(-1 * angleToRotate);
    return null;
  }


  /**
   * Turns the Turtle to the right by the specified number of degrees.
   *
   * @param angle the angle at which it should turn.
   * @return null.
   */
  public Object rt(String angle) throws NumberFormatException {
    int angleToRotate = Integer.parseInt(angle, 10);
    rotate(angleToRotate);
    return null;
  }

  /**
   * Puts the pen up, preventing the Turtle from drawing as it moves.
   *
   * @return null.
   */
  public Object pu() {
    this.penDown = false;
    return null;
  }

  /**
   * Puts the pen down, allowing the Turtle to draw as it moves.
   *
   * @return null.
   */
  public Object pd() {
    this.penDown = true;
    return null;
  }

  /**
   *  Makes the Turtle image invisible.
   *
   * @return null.
   */
  public Object ht() {
    myDisplayShape.setStroke(Color.TRANSPARENT);
    return null;
  }

  /**
   * Makes the Turtle visible.
   *
   * @return null.
   */
  public Object st() {
    myDisplayShape.setStroke(Color.BLUE);
    return null;
  }

  /**
   * Creates a non-moving duplicate of the turtle's image.
   *
   * @return the copy of the turtle.
   */
  public Object stamp() {
    Turtle copy = new Turtle();
    copy.setAngle(this.angle);
    Polygon copyShape = copy.getDisplayShape();
    copyShape.setStroke(copyShape.getStroke());
    copyShape.setLayoutX(copyShape.getLayoutX());
    copyShape.setLayoutY(copyShape.getLayoutY());
    return (copy);
  }

}
