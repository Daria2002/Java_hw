package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
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
		
		// check if arguments is quoted
		String[] argsArray;
		if(arguments.trim().contains("\"")) {
			argsArray = arguments.split("\"");
			
			if(argsArray.length != 2 || (!argsArray[0].isBlank() && !argsArray[0].isEmpty())) {
				
				for(int i = 0; i < argsArray.length; i++) {
					System.out.println(argsArray[i]);
				}
				
				System.out.println("Insert only one argument");
				return ShellStatus.CONTINUE;
			}
			
			arguments = argsArray[1];
		}

		File newFolder = new File(arguments);
	    if(!newFolder.exists()) {
	    	try {
				Files.createDirectory(Paths.get(newFolder.toString()));
			} catch (IOException e) {
				System.out.println("Error occured, so directory is not created.");
			}
	    }

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return MKDIR_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("The mkdir command takes a single argument: directory name.");
		list.add("Command creates the appropriate directory structure.");
		
        return Collections.unmodifiableList(list);
	}
}