package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class implements tree command. Tree command prints a tree for given directory.
 * @author Daria Matković
 *
 */
public class TreeCommand implements ShellCommand {

	/** tree command name **/
	public final static String TREE_COMMAND = "tree";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return TREE_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("The tree command expects a single argument: directory name.");
		list.add("Command prints a tree (each dir level shifts output two spaces to the right).");
		
        return Collections.unmodifiableList(list);
	}
}