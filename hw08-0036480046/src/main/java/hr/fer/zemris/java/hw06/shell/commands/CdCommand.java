package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Cd command gets one argument that represents new current directory.
 *
 */
public class CdCommand implements ShellCommand {

	/** cd command name **/
	public static final String CD_COMMAND = "cd";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Cd command gets one argument that represents new current directory.");
			return ShellStatus.CONTINUE;
		}
		
		String newCurrentDirectory = CommandUtilityClass.checkOneArgument(arguments);
		if(newCurrentDirectory != null) {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(Paths.get(newCurrentDirectory)));
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command pwd takes no arguments.");
		list.add("Prints absolute path to current directory.");
		
        return Collections.unmodifiableList(list);
	}
}