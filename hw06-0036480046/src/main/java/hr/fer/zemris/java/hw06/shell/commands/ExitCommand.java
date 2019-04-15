package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents exit command that stops main program execution.
 * @author Daria Matković
 *
 */
public class ExitCommand implements ShellCommand {

	/** exit command name **/
	public final static String EXIT_COMMAND = "exit";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return EXIT_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Shell terminates when user gives exit command.");
		
        return Collections.unmodifiableList(list);
	}
}
