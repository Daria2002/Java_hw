package hr.fer.zemris.java.hw06.shell;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
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

	/**
	 * This method is executed when program is run. It starts shell, where 
	 * user writes commands and program execute them.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

		buildMap(commands);
		
	}

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
