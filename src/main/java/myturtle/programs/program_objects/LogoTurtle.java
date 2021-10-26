package myturtle.programs.program_objects;

import java.util.ArrayList;
import java.util.List;
import myturtle.commands.Command;
import myturtle.error_handling.InvalidArgumentException;
import myturtle.error_handling.InvalidCommandException;


/**
 * An extension of the LogoTurtle class that takes in only
 * commands with 1 or 0 arguments.
 *
 * @author William Convertino
 */
public class LogoTurtle extends DrawingTurtle {


  /**
   * Constructs a new Turtle with a default shape pointed up.
   */
  public LogoTurtle () {
    setDisplayShape();
    this.angle = -90;
    this.penDown = true;
  }


  //Turns a command with multiple args into a list of single arg commands.
  private List<Command> getCommandListFromArgs(Command command) {

    ArrayList<Command> commandList = new ArrayList();
    if (!DEFAULT_DRAWING_COMMANDS.contains(command.getArgs().get(0))) {
      commandList.add(new Command(command.getCommand(), command.getArgs().get(0)));
      generateCommandListFromString(commandList, command.getArgs().subList(1,command.getArgs().size()), DEFAULT_DRAWING_COMMANDS);
    } else {
      commandList.add(new Command(command.getCommand()));
      generateCommandListFromString(commandList, command.getArgs().subList(1,command.getArgs().size()), DEFAULT_DRAWING_COMMANDS);
    }
    return commandList;

  }


  /**
   * Executes the given command if it has 1 or 0 args. Otherwise, it splits the command into
   * several smaller commands, each with 1 or 0 args.
   *
   * @param command the command to execute.
   * @return null.
   * @throws InvalidCommandException if an invalid command is passed.
   * @throws InvalidArgumentException if an invalid argument is given.
   */
  @Override
  public Object executeCommand (Command command) throws Exception {
    ArrayList<Object> nodeList = new ArrayList<>();
    if (command.getArgs().size() > 1) {
      for (Command c: getCommandListFromArgs(command)) {
        nodeList.add(super.executeCommand(c));
      }
    } else {
      nodeList.add(super.executeCommand(command));
    }
    return(nodeList);
  }

}
