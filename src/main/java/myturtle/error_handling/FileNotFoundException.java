package myturtle.error_handling;

/**
 * An exception to throw if the user attempted to access a file that the
 * system could not find.
 *
 * @author William Convertino
 */
public class FileNotFoundException extends java.io.FileNotFoundException {

  //The name of the file the user attempted to access.
  private String filename;

  /**
   * Constructs a new FileNotFoundException given the name of the file the user
   * attempted to access.
   *
   * @param filename the name of the file the user attempted to access.
   */
  public FileNotFoundException(String filename) {
    this.filename = filename;
  }

  /**
   * Returns the name of the file the user attempted to access.
   *
   * @return the name of the file the user attempted to access.
   */
  public String getFilename() {
    return filename;
  }

}
