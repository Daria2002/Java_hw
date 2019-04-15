package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents mkdir command. It creates the appropriate directory structure.
 * @author Daria Matković
 *
 */
public class MkdirCommand implements ShellCommand {

	public final static String MKDIR_COMMAND = "mkdir";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return MKDIR_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("The mkdir command takes a single argument: directory name.");
		list.add("Command creates the appropriate directory structure.");
		
        return Collections.unmodifiableList(list);
	}
}