package myturtle.display;

import java.awt.Dimension;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import myturtle.error_handling.FileNotFoundException;
import myturtle.error_handling.InvalidCommandException;

public class Display {

  public static final int VERT_PADDING = 30;
  public static final int BUTTON_SPACING = 10;
  public static final int GO_BUTTON_WIDTH = 40;
  private static final double CANVAS_WIDTH_FACTOR = 0.8;
  private static final double CANVAS_HEIGHT_FACTOR = 0.6;
  private static final String DEFAULT_RESOURCE_PACKAGE = "myturtle.display.resources.";
  //private static final String LANGUAGE = "English";
  private static final String DEFAULT_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/")+"Default.css";

  private Scene myScene;
  private Dimension myDimension;
  private ResourceBundle myResources;
  private DisplayPackage myDisplayPackage;

  private int canvasWidth;
  private int canvasHeight;
  private int commandWidth;
  private int sidePadding;
  private int vertPadding;

  private BorderPane root;
  private Group canvasDisplay;
  private Rectangle canvas;
  private VBox bottomPanel;
  private HBox nodePanel;
  private HBox commandLinePanel;
  private TextField commandDisplay;


  /**
   * Constructs a Display object
   *
   * @param stage the stage for our program's scene
   * @param dimension the dimension of our program's scene
   */
  public Display (Stage stage, Dimension dimension, String language) {
    setupDisplay(stage, dimension);
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }


  /**
   * Sets up the display of our program
   *
   * @param stage the stage for our program's scene
   * @param dimension the dimension of our program's scene
   */
  public void setupDisplay(Stage stage, Dimension dimension) {
    this.myDimension = dimension;
    root = new BorderPane();
    root.setId("Pane");
    myScene = new Scene(root, dimension.width, dimension.height);
    myScene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    root.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    bottomPanel = new VBox();
    initializeCanvas();
    initializeButtonPanel();
    root.setBottom(bottomPanel);
    stage.setScene(myScene);
    stage.show();
  }


  /**
   * Initializes button panel
   */
  public void initializeButtonPanel() {
    nodePanel = new HBox();
    sidePadding = (int) (myScene.getWidth() * (1-CANVAS_WIDTH_FACTOR)/2);
    vertPadding = VERT_PADDING;
    nodePanel.setSpacing(BUTTON_SPACING);
    nodePanel.setPadding(new Insets(0, sidePadding, vertPadding, sidePadding));
    bottomPanel.getChildren().add(nodePanel);
  }


  /**
   * Initializes canvas in display
   */
  public void initializeCanvas() {
    canvas = new Rectangle();
    canvasWidth = (int) (myScene.getWidth() * CANVAS_WIDTH_FACTOR);
    canvasHeight = (int) (myScene.getHeight() * CANVAS_HEIGHT_FACTOR);
    initializeCanvasProps();
    canvasDisplay = new Group(canvas);
    root.setCenter(canvasDisplay);
  }

  /**
   * Initializes the properties, such as height, width, and fill, of the canvas
   */
  private void initializeCanvasProps() {
    canvas.setWidth(canvasWidth);
    canvas.setHeight(canvasHeight);
    canvas.setId("my-canvas");
    canvas.setFill(Color.DARKGRAY);
    canvas.widthProperty().bind(root.widthProperty().multiply(CANVAS_WIDTH_FACTOR));
    canvas.heightProperty().bind(root.heightProperty().multiply(CANVAS_HEIGHT_FACTOR));
  }


  /**
   * Adds a Node to the display's canvas
   *
   * @param element the element to be added to the display
   * @param xPos the x position of the element
   * @param yPos the y position of the element
   */
  public void addNode(Node element, int xPos, int yPos) {
    if (canvasDisplay.getChildren().contains(element)) {
      return;
    }
    element.setLayoutX(xPos);
    element.setLayoutY(yPos);
    canvasDisplay.getChildren().add(element);
  }

  /**
   * Removes a Node from the display's canvas
   *
   *
   * @param element the element to be removed from the display
   */
  public void removeNodeFromDisplay(Node element) {
    if (!canvasDisplay.getChildren().contains(element)) {
      return;
    }
    canvasDisplay.getChildren().remove(element);
  }

  /**
   * Removes a Node from the node panel
   *
   *
   * @param element the element to be removed from the display
   */
  public void removeNodeFromNodePanel(Node element) {
    if (!nodePanel.getChildren().contains(element)) {
      return;
    }
    nodePanel.getChildren().remove(element);
  }

  /**
   * Adds a Node to the display
   */
  public void addNodeToPanel(Node b) {
    nodePanel.getChildren().add(b);
  }


  /**
   * Displays an error
   *
   * @param e the excpetion that will be displayed
   */
  public void showError(Exception e) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("ErrorTitle"));
    alert.setHeaderText(myResources.getString("ErrorHeader"));
    if (e instanceof InvalidCommandException) {
      alert.setContentText(String.format(myResources.getString("InvalidCommandException"), ((InvalidCommandException) e).getCommand()));
    } else if (e instanceof FileNotFoundException) {
      alert.setContentText(String.format(myResources.getString("FileNotFoundException"), ((FileNotFoundException) e).getFilename()));
    } else {
      alert.setContentText(myResources.getString("DefaultErrorMessage"));
    }
    alert.show();
  }

  /**
   * Adds the command line to the display
   *
   * @param myCommandLine the TextField that will represent the command line
   * @param e the event that occurs when a command is entered or when the 'Go' button is clicked
   */
  public void addCommandLine(TextField myCommandLine, EventHandler e) {
    commandDisplay = myCommandLine;
    commandLinePanel = new HBox();
    Button goButton = new Button();
    initializeCommandLineDisplay(e, goButton);
    bottomPanel.getChildren().addAll(commandLinePanel);
  }


  /**
   * Initializes the command line display
   *
   * @param e the event that occurs when a command is inputted into the command line
   * @param goButton the button to be displayed on the command line
   */
  public void initializeCommandLineDisplay(EventHandler e, Button goButton) {
    initializeGoButtonProps(e, goButton);
    initializeCommandLineProps(goButton);
  }


  /**
   * Initializes the properties, such as height, width, text, and event handler, of the Go button
   *
   * @param e the event handler that will be attached to the button
   * @param goButton the Button that represents the Go button
   */
  private void initializeGoButtonProps(EventHandler e, Button goButton) {
    goButton.setText(myResources.getString("GoButton"));
    goButton.setOnAction(e);
    goButton.setMinWidth(GO_BUTTON_WIDTH);
    goButton.setMaxWidth(GO_BUTTON_WIDTH);
  }


  /**
   * Initializes the properties, such as width and resizability, of the command line
   */
  private void initializeCommandLineProps(Button goButton) {
    commandWidth = canvasWidth - (int) goButton.getWidth();
    commandDisplay.setPrefColumnCount(commandWidth);
    commandDisplay.prefWidthProperty().bind(root.widthProperty());
    commandLinePanel.getChildren().add(commandDisplay);
    commandLinePanel.getChildren().add(goButton);
    commandLinePanel.setPadding(new Insets(0, sidePadding, vertPadding, sidePadding));
  }

  /**
   * Returns the button panel
   *
   * @return returns an HBox that is the button panel
   */
  public HBox getNodePanel() {
    return nodePanel;
  }

  /**
   * Returns the bottom panel
   *
   * @return returns an VBox that is the bottom panel
   */
  public VBox getBottomPanel() {
    return bottomPanel;
  }

  /**
   * Returns the canvas display
   *
   * @return returns a Group that is the canvas display
   */
  public Group getCanvasDisplay() {
    return canvasDisplay;
  }


  /**
   * Returns the root of the scene
   *
   * @return returns a BorderPane that is the root of the display
   */
  public Node getRoot() {
    return root;
  }

  /**
   * Adds all the elements of the given display package to the scene.
   *
   * @param p the display package to add.
   */
  public void loadDisplayPackage(DisplayPackage p) {
    if (p == null) {
      return;
    }
    unloadDisplayPackage();
    for (Node n: p.getElementList()) {
      addNode(n, (int)n.getLayoutX(), (int)n.getLayoutY());
    }
    myDisplayPackage = p;
  }

  /**
   * Removes all the elements of the current display package from the scene.
   *
   */
  public void unloadDisplayPackage() {
    if (myDisplayPackage == null) {
      return;
    }
    for (Node n: myDisplayPackage.getElementList()) {
      removeNodeFromDisplay(n);
    }
    myDisplayPackage = null;
  }


}
