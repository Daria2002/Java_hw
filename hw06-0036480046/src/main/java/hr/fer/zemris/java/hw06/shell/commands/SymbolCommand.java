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

	final static String SYMBOL_COMMAND = "symbol";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
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
