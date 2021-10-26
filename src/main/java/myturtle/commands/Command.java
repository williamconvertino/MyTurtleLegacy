package myturtle.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A data structure meant to store a passed command and its
 * arguments. This can be handled by the program to run user
 * commands.
 *
 * @author William Convertino
 **/
public class Command {

  private String command;
  private List<String> args;
  /**
   * Constructs a new command data type given a passed command
   * and arguments.
   *
   * @param command the passed command.
   * @param args the arguments of the command.
   */
  public Command(String command, String[] args) {
    this.command = command;
    this.args = new ArrayList<>(Arrays.asList(args));
  }

  /**
   * Constructs a new command data type given a passed command
   * and a single argument.
   *
   * @param command the passed command.
   * @param arg the argument of the command.
   */
  public Command(String command, String arg) {
    this.command = command;
    this.args = new ArrayList<>();
    this.args.add(arg);
  }


  /**
   * Constructs a new command data type using a passed command line.
   *
   * @param commandLine the command line to read, including the command and any arguments.
   */
  public Command (String commandLine) {
    if (commandLine == null) {
      this.command = null;
      return;
    } else if (commandLine.trim()=="") {
      this.command = "";
      this.args = new ArrayList<>();
    } else {
      String[] commandElements = commandLine.split(" ");
      this.command = commandElements[0].toLowerCase();

      if (commandElements.length > 2 && commandElements[2].substring(0,1).equals("\"")) {
        ArrayList<String> args = new ArrayList();
        args.add(commandElements[1]);
        args.add(commandLine.split("\"")[1]);
        this.args = args;
        return;
      }

      this.args = commandElements.length > 1 ? new ArrayList<>(
          Arrays.asList(Arrays.copyOfRange(commandElements, 1, commandElements.length)))
          : new ArrayList<>();
    }
  }

  /**
   * Returns the command stored.
   *
   * @return the stored command.
   */
  public String getCommand() {
    return command;
  }

  /**
   * Returns an ArrayList that contains all the passed arguments
   * of the command.
   *
   * @return the stored arguments.
   */
  public List<String> getArgs() {
    return args;
  }

  @Override
  public String toString() {
    String content = String.format("%s", command);
    for (String arg: args) {
      content += " " + arg;
    }

    return content;
  }


}
