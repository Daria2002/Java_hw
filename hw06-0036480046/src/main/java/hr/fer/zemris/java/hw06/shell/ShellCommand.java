package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that implements methods for commands.
 * @author Daria Matković
 *
 */
public interface ShellCommand {

	/**
	 * Method execute given command
	 * @param env object used for communication with user (read and write)
	 * @param arguments everything user entered after the command name
	 * @return ShellStatus. If command is exit returns ShellStatus.TERMINATE, 
	 * otherwise ShellStatus.CONTINUE
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Gets command name
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Gets description. List is used because description can span more than one line.
	 * @return list with command description
	 */
	List<String> getCommandDescription();
	
}
