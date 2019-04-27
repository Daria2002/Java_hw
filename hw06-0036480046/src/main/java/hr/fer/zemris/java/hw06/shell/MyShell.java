package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/** 
 * This class represents shell interface. It has main method where comunication
 * with user is done.
 * @author Daria Matković
 *
 */
public class MyShell {
	
	/** unmodifiable map, where key is command name and value command object **/
	private static SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
	private static Scanner commandScanner;
	
	/**
	 * This class is used for communication with user. It implements Environment
	 * interface so it can read user input and write response.
	 * @author Daria Matković
	 *
	 */
	private static class ShellEnviroment implements Environment {

		/** symbol at the end of previous line when command continues in next line. **/
		private static char morelinesSymbol = '\\';
		/** prompt symbol **/
		private static char promptSymbol = '>';
		/** symbol at beginning of line that is part of command started in previous line **/
		private static char multilineSymbol = '|';
		
		@Override
		public String readLine() throws ShellIOException {
			if(commandScanner.hasNext()) {
				return commandScanner.nextLine();
			}
			return null;
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.printf(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}
		
	}
	
	/**
	 * This method is executed when program is run. It starts shell, where 
	 * user writes commands and program execute them.
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		
		buildMap(commands);
		ShellEnviroment env = new ShellEnviroment();
		
		ShellStatus status = null;
		ShellCommand command;
		ArrayList<String> l = new ArrayList<String>();
		
		commandScanner = new Scanner(System.in);
		
		System.out.println("Welcome to MyShell v 1.0");
		
		do {
			try {
				// returns String array without morelines, multilines and prompt symbol
				l = readLineOrLines(commandScanner, env);
				
				if(l == null) {
					System.out.println("Something is wrong with command.");
					continue;
				}
				
				String commandName = l.get(0);
				String arguments = null;
				
				if(l.size() > 1) {
					// save everything, but command name
					arguments = String.join(" ", Arrays.copyOfRange(l.toArray(new String[l.size()]), 1, l.size()));
				}
				
				command = commands.get(commandName);
				
				status = command != null ? command.executeCommand(env, arguments) : 
					ShellStatus.CONTINUE;
				
				if(command == null) {
					System.out.println("Command " + commandName + " doesn't exists.");
				}
				
			} catch (Exception e) {
				status = ShellStatus.CONTINUE;
			}
	
		} while (status != ShellStatus.TERMINATE);
		
		commandScanner.close();
		System.exit(0);
	}

	/**
	 * Gets command without morelines, multilines and prompt symbols.
	 * @return String array with command name and command arguments
	 */
	private static ArrayList<String> readLineOrLines(Scanner commandScanner, 
			Environment env) {

		ArrayList<String> lines = new ArrayList<String>();
		String command;
		
		int i = 0;
		
		// this loop reads lines while morelinesSymbol is printed
		do {	
			System.out.print((i++ > 0 ? ShellEnviroment.multilineSymbol : 
				ShellEnviroment.promptSymbol) + " ");
			
			command = env.readLine().trim();
			
			if(command.contains("\"")) {
				ArrayList<String> help = new ArrayList<String>();
				help = readQuoted(command);
				
				for(int l = 0; l < help.size(); l++) {
					lines.add(help.get(l));
				}
				
				if(String.valueOf(ShellEnviroment.morelinesSymbol).equals(help.get(help.size()-1))) {
					continue;
				}
				
				break;
			}
			
			String[] commandArray = command.split(" ");
			
			// this loop adds every word of command lines like new element of lines
			for(int k = 0; k < commandArray.length; k++) {
				
				if(commandArray[k] == null || commandArray[k].isBlank()
						|| String.valueOf(ShellEnviroment.morelinesSymbol).equals(commandArray[k])) {
					break;
				}
				
				lines.add(commandArray[k].toString());
			}
			
		} while (command.indexOf(ShellEnviroment.morelinesSymbol) == command.length()-1);
		
		//String[] helpArray = new String[lines.size()];
		ArrayList<String> helpArray = new ArrayList<String>();
		int index = 0;
		for(int n = 0; n < lines.size(); n++) {
			if(String.valueOf(ShellEnviroment.morelinesSymbol).equals(lines.get(n)) || 
					lines.get(n) == null) {
				continue;
			}
			
			helpArray.add(lines.get(n));
		}
		
		// returns linesArray
		return helpArray;
	}
	
	/**
	 * This method reads line where quoted path occurred
	 * @param command line with quoted path
	 * @return ArrayList with quoted and unquoted elements in command with quotes
	 */
	private static ArrayList<String> readQuoted(String command) {
		// array where commands are split with ", it means that every other
		// element is in quotes
		String[] commandArray = command.split("\"");
		ArrayList<String> lines = new ArrayList<String>();
		
		ArrayList<String> quoteList = new ArrayList<String>();
		// adding quoted elements
		for(int i = 1; i < commandArray.length; i = i + 2) {
			quoteList.add("\"" + commandArray[i] + "\"");
		}
		
		String stringToAdd;
		// adding unquoted and quoted elements in array
		for(int i = 0; i < commandArray.length; i++) {
			
			// adding quoted elements
			if(i%2 != 0 && quoteList.size() > i/2) {
				stringToAdd = quoteList.get(i/2);
				
				if(stringToAdd.isBlank() || stringToAdd.isEmpty()) {
					continue;
				}
				
				lines.add(quoteList.get(i/2));
				continue;
			}
			
			// unquoted elements need to be split with " "
			String[] splited = commandArray[i].split(" ");
			
			for(int m = 0; m < splited.length; m++) {
				stringToAdd = splited[m];
				
				if(stringToAdd.isBlank() || stringToAdd.isEmpty() || stringToAdd == null) {
					continue;
				}
				
				lines.add(splited[m]);
			}
		}
		
		return lines;
	}
	
	/**
	 * This method adds all commands in map, so map key is command name and map
	 * value is new Command object
	 * @param commands map that needs to be filled with commands
	 */
	private static void buildMap(Map<String, ShellCommand> commands) {
		
		commands.put(CharsetsCommand.CHARSETS_COMMAND, new CharsetsCommand());
		commands.put(CatCommand.CAT_COMMAND, new CatCommand());
		commands.put(LsCommand.LS_COMMAND, new LsCommand());
		commands.put(TreeCommand.TREE_COMMAND, new TreeCommand());
		commands.put(CopyCommand.COPY_COMMAND, new CopyCommand());
		commands.put(MkdirCommand.MKDIR_COMMAND, new MkdirCommand());
		commands.put(HexdumpCommand.HEXDUMP_COMMAND, new HexdumpCommand());
		commands.put(SymbolCommand.SYMBOL_COMMAND, new SymbolCommand());
		commands.put(HelpCommand.HELP_COMMAND, new HelpCommand());
		commands.put(ExitCommand.EXIT_COMMAND, new ExitCommand());
	}
}
