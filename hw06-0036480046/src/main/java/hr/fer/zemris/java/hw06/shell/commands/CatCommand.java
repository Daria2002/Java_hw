package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command represents cat command that opens given file and write its content
 * to console. This class implements ShellComand interface.
 * @author Daria Matković
 *
 */
public class CatCommand implements ShellCommand {

	static final String CAT_COMMAND = "cat";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CAT_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset should be used.");
		list.add("This command opens given file and writes its content to console.");
		
        return Collections.unmodifiableList(list);
	}
}