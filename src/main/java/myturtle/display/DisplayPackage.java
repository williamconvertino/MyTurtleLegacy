package myturtle.display;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;

/**
 * A data structure to store a program's JavaFX elements that will
 * later be displayed on the display.
 *
 * @author William Convertino
 */
public class DisplayPackage {

  LinkedList<Node> elementList;

  public DisplayPackage() {
    this.elementList = new LinkedList<>();
  }

  /**
   * Adds an element to the element list.
   *
   * @param n the element to add.
   */
  public void add(Node n) {
    elementList.add(n);
  }

  public void addFirst(Node n) {
    elementList.addFirst(n);
  }

  /**
   * Returns the elementList.
   *
   * @return an ArrayList of all the JavaFX items in the package.
   */
  public List<Node> getElementList() {
    return elementList;
  }

}
