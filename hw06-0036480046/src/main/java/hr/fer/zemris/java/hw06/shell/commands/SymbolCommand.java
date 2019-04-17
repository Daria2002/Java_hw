package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
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
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] args = arguments.split(" ");
		
		
		String currentValue;
		// print info message
		if(args.length == 1) {
			if("PROMPT".equals(args[0])) {
				currentValue = String.valueOf(env.getPromptSymbol());
				
			} else if("MORELINES".equals(args[0])) {
				currentValue = String.valueOf(env.getMorelinesSymbol());
				
			} else if("MULTILINE".equals(args[0])) {
				currentValue = String.valueOf(env.getMultilineSymbol());
				
			} else {
				System.out.println("Symbol " + args[0] + " doesn't exists.");
				return ShellStatus.CONTINUE;
			}
			System.out.println("Symbol for " + args[0] + " is '" + currentValue + "'");
			
		} else if(args.length == 2) {
			if("PROMPT".equals(args[0])) {
				System.out.println("Symbol for " + args[0] +
						" changed from '" + env.getPromptSymbol().toString()  + "' to '" + args[1] + "'");
				env.setPromptSymbol(args[1].charAt(0)); 
				  
			} else if("MORELINES".equals(args[0])) {
				System.out.println("Symbol for " + args[0] +
						" changed from '" + env.getMorelinesSymbol().toString()  + "' to '" + args[1] + "'");
				env.setMorelinesSymbol(args[1].charAt(0)); 
				
			} else if("MULTILINE".equals(args[0])) {
				System.out.println("Symbol for " + args[0] +
						" changed from '" + env.getMultilineSymbol().toString()  + "' to '" + args[1] + "'");
				env.setMultilineSymbol(args[1].charAt(0)); 
				
			} else {
				System.out.println("Symbol " + args[0] + " doesn't exists.");
				return ShellStatus.CONTINUE;
			}
		}
		
		
		return ShellStatus.CONTINUE;
	}

	private String getValue(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandName() {
		return SYMBOL_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Command copy takes one or two arguments.");
		list.add("First argument is symbol name and this argument is mandatory.");
		list.add("Second argument is char and symbol value should be set to this value");
		list.add("Second argument is optional and if there is only one argument message prints.");
		list.add("Printed message says what current symbol value is.");
		
        return Collections.unmodifiableList(list);
	}

}
