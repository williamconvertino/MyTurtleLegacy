package myturtle.programs.program_objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import myturtle.commands.CommandReader;

/**
 *  This class acts as an all-purpose drawing agent. When given commands, it can move, rotate,
 *  draw, and more.
 *
 * @author William Convertino
 */
public class Turtle extends CommandReader {

  //The shape representing the Turtle that is to be displayed.
  protected Polygon myDisplayShape;

  //The angle (in degrees) of the turtle.
  protected int angle;

  /**
   * Constructs a new Turtle with a default shape pointed up.
   */
  public Turtle () {
    this.myDisplayShape = new Polygon();
    this.angle = -90;
    setDisplayShape();
  }
  //Creates a default display shape for the turtle.
  protected void setDisplayShape() {
    myDisplayShape = new Polygon();
    myDisplayShape.getPoints().addAll(2.0, 2.0,
        7.0, -10.0,
        12.0, 2.0);
    myDisplayShape.setScaleX(1.0);
    myDisplayShape.setScaleY(1.0);
    home();
    myDisplayShape.setFill(Color.TRANSPARENT);
    myDisplayShape.setStroke(Color.BLUE);
  }

  /**
   * Returns the display shape of this Turtle.
   *
   * @return the display shape of this Turtle.
   */
  public Polygon getDisplayShape () {
    return myDisplayShape;
  }

  /**
   * Sets the angle of the turtle.
   *
   * @param angle the angle at which the turtle should face.
   */
  public void setAngle(int angle) {
    this.angle = angle;
  }

  /**
   * Moves the Turtle to its home location.
   *
   * @return null.
   */
  public Object home() {
    myDisplayShape.setLayoutX(320);
    myDisplayShape.setLayoutY(270);
    return null;
  }



}
