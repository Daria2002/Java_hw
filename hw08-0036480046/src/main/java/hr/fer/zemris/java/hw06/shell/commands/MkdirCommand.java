package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	/** mkdir command name **/
	public final static String MKDIR_COMMAND = "mkdir";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Insert only one argument");
			return ShellStatus.CONTINUE;
		}
		
		String[] args = CommandUtilityClass.checkArguments(arguments, 1);
		
		if(args.length != 1) {
			env.writeln("Arguments are not valid");
			return ShellStatus.CONTINUE;
		}
		
		arguments = args[0].toString();

		File newFolder = new File(CommandUtilityClass.resolvePath(arguments, env));
	    if(!newFolder.exists()) {
	    	try {
				Files.createDirectory(Paths.get(newFolder.toString()));
			} catch (IOException e) {
				env.writeln("Error occured, so directory is not created.");
			}
	    } else {
	    	env.writeln("Folder already exists.");
	    }

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return MKDIR_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("The mkdir command takes a single argument: directory name.");
		list.add("Command creates the appropriate directory structure.");
		
        return Collections.unmodifiableList(list);
	}
}