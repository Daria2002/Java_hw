package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents charsets command that list names of supported charsets
 * for user's Java platform. This class implements ShellCommand interface.
 * @author Daria Matković
 *
 */
public class CharsetsCommand implements ShellCommand{

	public final static String CHARSETS_COMMAND = "charsets";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CHARSETS_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Command charsets takes no arguments and lists names of supported charsets.");
		list.add("A single charset name is written per line.");
		
        return Collections.unmodifiableList(list);
	}
}