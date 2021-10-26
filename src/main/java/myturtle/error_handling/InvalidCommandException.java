package myturtle.error_handling;

import myturtle.commands.Command;

/**
 *  An Exception to throw if an invalid command has been passed.
 *
 * @author William Convertino
 */
public class InvalidCommandException extends IllegalArgumentException {

  //The invalid command that was called.
  private Command command;

  /**
   * Constructs a new InvalidCommandException given the command that was run.
   *
   * @param command the invalid command that was called.
   */
  public InvalidCommandException (Command command) {
    this.command = command;
  }


  /**
   * Returns the invalid command that was called.
   *
   * @return the invalid command that was called.
   */
  public Command getCommand() {
    return command;
  }

}
