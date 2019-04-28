package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import hr.fer.zemris.java.hw06.shell.Environment;

/**
 * This class contains methods that are common in more command classes
 * @author Daria Matković
 *
 */
public class CommandUtilityClass {
	
	public static String resolvePath(String path, Environment env) {
		if(env.getCurrentDirectory() != null) {
			return Paths.get(env.getCurrentDirectory().toString()).
					resolve(Paths.get(path)).toString();
		}
		return Paths.get(path).toString();
	}
	
	/**
	  * Method checks argument for command where need to be two arguments. It checks if arguments are 
	 * quoted and returns string array of unquoted arguments
	 * @param arguments string with arguments
	 * @param numberOfArgs max number of arguments needed for command
	 * @return string array with arguments if input is ok, otherwise null
	 */
	public static String[] checkArguments(String arguments, int numberOfArgs) {
		ArrayList<String> argumentList = parseWithEscape(arguments);
		
		if(argumentList == null || numberOfArgs != argumentList.size()) {
			return null;
		}
		
		return argumentList.toArray(new String[argumentList.size()]);
	}
	
	/**
	 * This method parse command with escaping in quotes
	 * @param arguments command without command name
	 * @return ArrayList<String> that represents where each, null if error occurred
	 */
	public static ArrayList<String> parseWithEscape(String arguments) {
		int i = 0;
		char[] commandCharArray = arguments.trim().replace("\\s+", " ").toCharArray();
		
		boolean stringSequence = false;
		boolean escapeSequence = false;
		
		ArrayList<String> argumentsElements = new ArrayList<String>();
		StringBuilder buildArgument = new StringBuilder();
		
		do {
			// escape if escaping sequence occurs
			if(escapeSequence && stringSequence && (commandCharArray[i] == '\\' || 
					commandCharArray[i] == '"')) {
				escapeSequence = false;
				buildArgument.append(commandCharArray[i]);
			}
			
			// write two chars if not escape sequence
			else if(escapeSequence && (commandCharArray[i] != '\\' || commandCharArray[i] != '"')) {
				buildArgument.append('\\');
				buildArgument.append(commandCharArray[i]);
				escapeSequence = false;
			}
			
			// string sequence starts when string sequence is false and " occurs
			else if(commandCharArray[i] == '"' && !stringSequence && !escapeSequence) {
				stringSequence = true;
				
				// everything before " add as argument if there was something before "
				if(buildArgument.length() != 0 && buildArgument.toString() != " ") {
					argumentsElements.add(buildArgument.toString());
					buildArgument = new StringBuilder();
				}
			}
			
			// if escapeSequence occurs outside of string sequence return null	
			else if(commandCharArray[i] == '\\' && !stringSequence) {
				return null;
			}
			
			// escapeSequence starts if \ occurred in quotes and escapeSequence was false
			else if(commandCharArray[i] == '\\' && stringSequence && !escapeSequence) {
				escapeSequence = true;
			}
			
			// string sequence stops if " occurs in quotes when escape sequence is false
			else if(stringSequence && !escapeSequence && commandCharArray[i] == '"') {
				stringSequence = false;
				
				if(buildArgument.length() != 0 && buildArgument.toString() != " ") {
					argumentsElements.add(buildArgument.toString());
					buildArgument = new StringBuilder();
				}
			}
			
			// if ' ' occurs outside of string sequence add built argument
			else if(commandCharArray[i] == ' ' && !stringSequence) {
				if(buildArgument.length() != 0 && buildArgument.toString() != " ") {
					argumentsElements.add(buildArgument.toString());
					buildArgument = new StringBuilder();
				}
			}
			
			else {
				buildArgument.append(commandCharArray[i]);
			}
			
		} while(i++ < commandCharArray.length-1);
		
		if(buildArgument.length() != 0 && buildArgument.toString() != " ") {
			argumentsElements.add(buildArgument.toString());
		}
		
		return argumentsElements;
	}
}
