package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;

import hr.fer.zemris.java.hw06.shell.Environment;

/**
 * This class contains methods that are common in more command classes
 * @author Daria Matković
 *
 */
public class CommandUtilityClass {

	/**
	 * This class checks argument for commands where need to be only one argument. It checks if
	 * argument is quoted, and if it is returns argument without quotes.
	 * @param arguments argument to check
	 * @return argument without quotes if input is ok, otherwise null
	 */
	public static String checkOneArgument(String arguments) {
		String[] argsArray;
		
		// remove " 
		if(arguments.trim().contains("\"")) {
			argsArray = arguments.split("\"");
			
			if(argsArray.length != 2 || (!argsArray[0].isBlank() && !argsArray[0].isEmpty())) {
				return null;
			}
			
			arguments = argsArray[1];
		// check if only one argument is given
		} else {
			argsArray = arguments.split(" ");
			
			if(argsArray.length != 1) {
				return null;
			}
		}
		
		return arguments;
	}
	
	public static String resolvePath(String path, Environment env) {
		return env.getCurrentDirectory().resolve(Paths.get(path)).toString();
	}
	
	/**
	  * Method checks argument for command where need to be two arguments. It checks if arguments are 
	 * quoted and returns string array of unquoted arguments
	 * @param arguments string with arguments
	 * @param numberOfArgs max number of arguments needed for command
	 * @return string array with arguments if input is ok, otherwise null
	 */
	public static String[] checkTwoArguments(String arguments, int numberOfArgs) {
		String[] argsArray;
		
		if(arguments.contains("\"")) {
			argsArray = arguments.split("\"");
		} else {
			argsArray = arguments.split(" ");
		}
		
		String[] data = new String[numberOfArgs];
		int index = 0;
		
		for(int i = 0; i < argsArray.length; i++) {
			if(argsArray[i].isBlank() || argsArray[i].isEmpty() || argsArray[i] == null) {
				continue;
			}
			
			if(index >= numberOfArgs) {
				System.out.println("Number of arguments command takes: " + numberOfArgs);
				return null;
			}
			
			data[index++] = argsArray[i];
		}
		
		return data;
	}
}
