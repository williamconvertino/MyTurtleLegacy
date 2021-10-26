package myturtle.display;

import java.util.Random;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;


public class Wildlife extends Shape {

  private static final double SHAPE_WIDTH = 20;
  private static final double SHAPE_HEIGHT = 20;

  private static final Color[] randomColorOptions = {Color.ORANGE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BROWN, Color.PURPLE, Color.BLACK};

  public static Rectangle buildRectangle(Color color) {
    Rectangle rectangle = new Rectangle();
    rectangle.setWidth(SHAPE_WIDTH);
    rectangle.setHeight(SHAPE_HEIGHT);
    rectangle.setFill(color);
    return rectangle;
  }

  public static Circle buildCircle(Color color) {
    Circle circle = new Circle();
    circle.setRadius(SHAPE_WIDTH/2);
    circle.setFill(color);
    return circle;
  }

  public static Polygon buildStar(Color color) {
    Polygon star = new Polygon();
    star.getPoints().addAll(2.0, 6.0,
        2.75, 4.0,
        4.0, 4.0,
        3.0, 3.0,
        3.5, 1.0,
        2.0, 2.0,
        0.5, 1.0,
        1.0, 3.0,
        0.0, 4.0,
        1.25, 4.0,
        2.0, 6.0);
    star.setFill(color);
    star.setScaleX(8.0);
    star.setScaleY(8.0);
    return star;
  }

  public static Polygon buildTriangle(Color color) {
    Polygon triangle = new Polygon();
    triangle.getPoints().addAll(2.0, 2.0,
        7.0, 10.0,
        12.0, 2.0);
    triangle.setScaleX(6.0);
    triangle.setScaleY(6.0);
    triangle.setFill(color);
    return triangle;
  }

  public static Shape buildRandomShape(Color color) {
    Random rand = new Random();
    int randInt = rand.nextInt(4);
    Shape[] shapes = {buildRectangle(color), buildCircle(color), buildStar(color), buildTriangle(color)};
    return shapes[randInt];
  }


}
