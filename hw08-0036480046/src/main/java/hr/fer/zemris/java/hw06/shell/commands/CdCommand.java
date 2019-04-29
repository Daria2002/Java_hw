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
		
		String[] args = CommandUtilityClass.checkArguments(arguments, 1);
		
		if(args != null && args.length == 1) {
			String newCurrentDirectory = args[0].toString();
			env.setCurrentDirectory(Paths.get(CommandUtilityClass.resolvePath(newCurrentDirectory, env)));
		} else {
			env.writeln("Arguments are not valid.");
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
		
		list.add("Command cd takes one argument that represents new current dir.");
		list.add("Cd command sets given dir as new current dir.");
		
        return Collections.unmodifiableList(list);
	}
}