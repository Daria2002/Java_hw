package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents ls command that writes directory listing in four columns.
 * First column indicates if currents object is directory (d), (readable) r, (writable) w, (executable) e 
 * Second column contains object size in bytes
 * Third column is file creation date/time
 * Forth column is file name
 * @author Daria Matković
 *
 */
public class LsCommand implements ShellCommand {

	/** ls command name **/
	public final static String LS_COMMAND = "ls";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return LS_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Command ls takes a single argument – directory.");
		
        return Collections.unmodifiableList(list);
	}
}