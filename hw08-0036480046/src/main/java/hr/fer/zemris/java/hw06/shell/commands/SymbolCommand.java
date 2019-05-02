package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents command symbol. This command read or writes symbol value.
 * @author Daria Matković
 *
 */
public class SymbolCommand implements ShellCommand {

	/** symbol command name **/
	public final static String SYMBOL_COMMAND = "symbol";
	/** array of symbol names **/
	private String[] symbolArray = new String[3];
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		/** initialize symbolArray for checking given symbol name **/
		symbolArray[0] = "PROMPT";
		symbolArray[1] = "MORELINES";
		symbolArray[2] = "MULTILINE";
		
		String[] args = arguments.split(" ");
		String currentValue = null;
		
		// check symbol name
		if(!Arrays.asList(symbolArray).contains(args[0])) {
			env.writeln("Symbol " + args[0] + " doesn't exists.");
		}
		
		// print info message
		else if(args.length == 1) {
			if("PROMPT".equals(args[0])) {
				currentValue = String.valueOf(env.getPromptSymbol());
				
			} else if("MORELINES".equals(args[0])) {
				currentValue = String.valueOf(env.getMorelinesSymbol());
				
			} else if("MULTILINE".equals(args[0])) {
				currentValue = String.valueOf(env.getMultilineSymbol());
			}
			
			if(currentValue != null) {
				env.writeln("Symbol for " + args[0] + " is '" + currentValue + "'");
			}
			
		} else if(args.length == 2) {
			if(args[1].length() > 1) {
				env.writeln("New value for symbol " + args[0] + " must have length 1");
			}
			
			else if("PROMPT".equals(args[0])) {
				changeSymbolMessage(env, args[0], args[1], env.getPromptSymbol().toString());
				env.setPromptSymbol(args[1].charAt(0)); 
				  
			} else if("MORELINES".equals(args[0])) {
				changeSymbolMessage(env, args[0], args[1], env.getMorelinesSymbol().toString());
				env.setMorelinesSymbol(args[1].charAt(0)); 
				
			} else if("MULTILINE".equals(args[0])) {
				changeSymbolMessage(env, args[0], args[1], env.getMultilineSymbol().toString());
				env.setMultilineSymbol(args[1].charAt(0)); 
			}
		}
		
		return ShellStatus.CONTINUE;
	}
	
	private void changeSymbolMessage(Environment env, String symbolName, String newSymbol, 
			String oldSymbol) {
		env.writeln("Symbol for " + symbolName + " changed from '" + oldSymbol +
				"' to '" + newSymbol + "'");
	}

	@Override
	public String getCommandName() {
		return SYMBOL_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command symbol takes one or two arguments.");
		list.add("First argument is symbol name and this argument is mandatory.");
		list.add("Second argument is char and symbol value should be set to this value");
		list.add("Second argument is optional and if there is only one argument message prints.");
		list.add("Printed message says what current symbol value is.");
		
        return Collections.unmodifiableList(list);
	}
}
