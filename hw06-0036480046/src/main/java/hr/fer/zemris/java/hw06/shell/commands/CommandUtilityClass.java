package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class contains methods that are common in more command classes
 * @author Dariia Matković
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
		
		if(arguments.trim().contains("\"")) {
			argsArray = arguments.split("\"");
			
			if(argsArray.length != 2 || (!argsArray[0].isBlank() && !argsArray[0].isEmpty())) {
				
				for(int i = 0; i < argsArray.length; i++) {
					System.out.println(argsArray[i]);
				}
				
				return null;
			}
			
			arguments = argsArray[1];
		}
		
		return arguments;
	}
	
	/**
	 * Method checks argument for command where need to be two arguments. It checks if arguments are 
	 * quoted and returns string array of unquoted arguments
	 * @param arguments string with arguments
	 * @return string array with arguments if input is ok, otherwise null
	 */
	public static String[] checkTwoArguments(String arguments) {
		String[] argsArray;
		
		if(arguments.contains("\"")) {
			argsArray = arguments.split("\"");
		} else {
			argsArray = arguments.split(" ");
		}
		
		String[] data = new String[2];
		int index = 0;
		
		for(int i = 0; i < argsArray.length; i++) {
			if(argsArray[i].isBlank() || argsArray[i].isEmpty()) {
				continue;
			}
			
			if(index >= 2) {
				return null;
			}
			
			data[index++] = argsArray[i];
		}
		
		return data;
	}
}
