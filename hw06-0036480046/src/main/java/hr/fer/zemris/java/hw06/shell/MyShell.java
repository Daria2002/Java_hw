package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	/** symbol at the end of previous line when command continues in next line. **/
	private static char morelinesSymbol = '\\';
	/** prompt symbol **/
	private static char promptSymbol = '>';
	/** symbol at beginning of line that is part of command started in previous line **/
	private static char multilineSymbol = '|';
	
	/**
	 * This class is used for communication with user. It implements Environment
	 * interface so it can read user input and write response.
	 * @author Daria Matković
	 *
	 */
	private static class ShellEnviroment implements Environment {

		@Override
		public String readLine() throws ShellIOException {
			if(commandScanner.hasNext()) {
				return commandScanner.nextLine();
			}
			return null;
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
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
		
		ShellStatus status;
		ShellCommand command;
		String[] l = new String[3];
		int nullIndex = 0;
		
		commandScanner = new Scanner(System.in);
		
		System.out.println("Welcome to MyShell v 1.0");
		
		do {
			// returns String array without morelines, multilines and prompt symbol
			l = readLineOrLines(commandScanner, env);
			
			// null values are not saved as arguments
			nullIndex = getNullIndex(l) == -1 ? l.length : getNullIndex(l);
			
			String commandName = l[0];
			String arguments = null;
			
			if(l.length > 1) {
				// save everything, but null values and command name
				arguments = String.join(" ", Arrays.copyOfRange(l, 1, nullIndex));
			}
			
			command = commands.get(commandName);
			
			status = command != null ? command.executeCommand(env, arguments) : 
				ShellStatus.CONTINUE;
			
		} while (status != ShellStatus.TERMINATE);
		
		commandScanner.close();
	}

	private static int getNullIndex(String[] l) {
		for(int i = 0; i < l.length; i++) {
			if(l[i] == null) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets command without morelines, multilines and prompt symbols.
	 * @return String array with command name and command arguments
	 */
	private static String[] readLineOrLines(Scanner commandScanner, 
			Environment env) {

		ArrayList<String> lines = new ArrayList<String>();
		String command;
		
		int i = 0;
		do {	
			System.out.print(i > 0 ? multilineSymbol : promptSymbol);
			
			command = env.readLine().trim();
			String[] commandArray = command.split(" ");
			
			for(int k = 0; k < commandArray.length; k++) {
				
				if(commandArray[k] == null || commandArray[k].isBlank()
						|| String.valueOf(morelinesSymbol).equals(commandArray[k])) {
					break;
				}
				
				lines.add(i++, commandArray[k]);
			}
			
		} while (command.indexOf(morelinesSymbol) == command.length()-1);
		
		String[] linesArray = new String[lines.size()];
		
		return lines.toArray(linesArray);
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
