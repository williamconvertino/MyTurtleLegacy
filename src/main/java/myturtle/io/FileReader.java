package myturtle.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import myturtle.commands.Command;

/**
 *  A file reading class that is capable of reading program data
 *  files and returning them as a list of commands.
 *
 * @author William Convertino
 * @author Luke Josephy
 */


public class FileReader {

  private Path path = FileSystems.getDefault().getPath("data");
  private String pathname = path.toString();

  private BufferedReader myFileReader;

  /**
   *  Constructs a new FileReader from a given file name. If the
   *  file can not be found, it throws a FileNotFoundException.
   *
   * @param fileName the name of the file to be read (must be located within the data folder)
   * @throws FileNotFoundException if the specified filename cannot be found.
   */

  public FileReader(String fileName) throws FileNotFoundException {

    InputStream is = getClass().getResourceAsStream("/" + fileName);
    if (is != null) {
      myFileReader = new BufferedReader(new InputStreamReader(is));
    } else {
      throw new FileNotFoundException();
    }

  }

  /**
   * Returns an arraylist of all the commands in the FileReader's file.
   *
   * @return An ArrayList of all the commands in the file (in the order they are written).
   * @throws IOException if the file is somehow moved or corrupted while being read.
   */
  public List<Command> getCommandList() throws IOException {

    ArrayList<Command> myCommands = new ArrayList<>();
    String currentLine;

    while ((currentLine = myFileReader.readLine()) != null) {
      if (currentLine.trim().length() > 0 &&  !currentLine.substring(0,1).equals("#")) {
        myCommands.add(new Command(currentLine));
      }
    }
    return(myCommands);
  }

}
