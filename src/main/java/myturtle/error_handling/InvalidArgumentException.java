package myturtle.error_handling;

import myturtle.commands.Command;

/**
 *  An Exception to throw if an invalid argument has been passed to a command.
 *
 * @author William Convertino
 */
public class InvalidArgumentException extends IllegalArgumentException {

  //The command that was passed.
  private Command myCommand;

  /**
   * Constructs a new InvalidArgumentException given the command that caused the exception.
   *
   * @param myCommand the command that caused the exception.
   */
  public InvalidArgumentException (Command myCommand) {
    this.myCommand = myCommand;
  }

  /**
   * Returns the command that caused the exception.
   *
   * @return the command that caused the exception.
   */
  public Command getCommand() {
    return myCommand;
  }

}
