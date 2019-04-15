package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents hexdump command that produces hex-output for given file name.
 * @author Daria Matković
 *
 */
public class HexdumpCommand implements ShellCommand {

	final static String HEXDUMP_COMMAND = "hexdump";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return HEXDUMP_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
