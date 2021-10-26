package myturtle.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.error_handling.InvalidCommandException;

public class CommandReader {

  /**
   * Returns the negated version of a string. In other words, if the string represents a
   * negative integer, it becomes positive, and vice versa.
   *
   * @param argument the argument to negate.
   * @return the negated version of the argument.
   */
  public static String negate(String argument) {
    if (argument == null || argument.length() == 0) {
      return argument;
    }
    if (argument.substring(0,1).equals("-")) {
      return argument.substring(1,argument.length());
    } else {
      return String.format("-%s", argument);
    }
  }

  /**
   * Executes a command given a command data type.
   *
   * @param command the command to execute.
   * @return any objects returned by the executed command.
   * @throws InvalidCommandException if an invalid command is given.
   * @throws InvalidArgumentException if an invalid argument is passed.
   */
  public Object executeCommand(Command command) throws Exception {

    //If the command is not to be run, return null.
    if (command == null || command.getCommand().equals("") || command.getCommand().substring(0,1).equals("#")) {
      return null;
    }

    //Otherwise, attempt to run the command.
    try {
      if (command.getArgs().size() == 2) {
        Method myMethod = getClass().getMethod(command.getCommand(), String.class, String.class);
        return myMethod.invoke(this, command.getArgs().get(0), command.getArgs().get(1));
      } else if (command.getArgs().size() == 1) {
        Method myMethod = getClass().getMethod(command.getCommand(), String.class);
        return myMethod.invoke(this,command.getArgs().get(0));

      } else if (command.getArgs().size() == 0) {
        Method myMethod = getClass().getMethod(command.getCommand());
        return myMethod.invoke(this);

      } else {
        throw new InvalidArgumentException(command);
      }

      //If the command is not present, throw an Invalid Command exception.
    } catch (NoSuchMethodException e) {
      throw new InvalidCommandException(command);
    } catch (InvalidCommandException e) {
      throw e;
    } catch (Exception e) {
      throw new InvalidArgumentException(command);
    }
  }


  //Given a list of commands and arguments, adds each command (with 1 or 0 args) to the given list.
  protected void generateCommandListFromString(List<Command> ret, List<String> remaining, Set VALID_COMMANDS) {

    if (remaining == null||remaining.size() == 0) {
      return;
    }
    if (remaining.size() == 1) {
      ret.add(new Command(remaining.get(0)));
      return;
    }
    if (VALID_COMMANDS.contains(remaining.get(1))) {
      ret.add(new Command(remaining.get(0)));
      generateCommandListFromString(ret, remaining.subList(1, remaining.size()), VALID_COMMANDS);
    } else {
      ret.add(new Command(remaining.get(0), remaining.get(1)));
      generateCommandListFromString(ret, remaining.subList(2, remaining.size()), VALID_COMMANDS);
    }

  }

  /**
   * Executes a list of commands.
   *
   * @param commandList the list of commands to execute.
   * @return a list of the returned objects.
   * @throws Exception if a command is not recognized.
   */
  public List<Object> executeCommand(List<Command> commandList) throws Exception {
    ArrayList<Object> objectList = new ArrayList<>();
    for (Command c: commandList) {
      objectList.add(executeCommand(c));
    }
    return objectList;
  }

}
