package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents copy command and implements ShellCommand interface.
 * Copy command copies source file to destination file.
 * @author Daria Matković
 *
 */
public class CopyCommand implements ShellCommand {

	final static String COPY_COMMAND = "copy";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COPY_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
