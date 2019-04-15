package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
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

	/** hexdump command name **/
	public final static String HEXDUMP_COMMAND = "hexdump";
	
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
		List list = new ArrayList();
		
		list.add("Takes one argument: file name, and produces hex-output.");
		list.add("Bytes whose value is less than 32 or greater than 127 are printed."); 
		list.add(" otherwise '.' is printed instead of char.");
		list.add("Printed message says what current symbol value is.");
		
        return Collections.unmodifiableList(list);
	}
}