package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Dropd command takes dir path from top of stack and removes it. Current dir doesn't
 * change. If stack empty, error message occurs. Command takes no argument.
 * @author Daria Matković
 *
 */
public class DropdCommand implements ShellCommand {

	/** cat command name **/
	public static final String DROPD_COMMAND = "dropd";
	/** key for stack in shared data **/
	public static final String STACK_KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments != null) {
			env.writeln("Command popd doesn't take any arguments");
			return ShellStatus.CONTINUE;
		}
		
		Stack sharedStack = (Stack) env.getSharedData(STACK_KEY);
		
		// is stack doesn't exist or there is no path on stack
		if(sharedStack == null || sharedStack.isEmpty()) {
			env.writeln("There is no path on stack");
			return ShellStatus.CONTINUE;
		}
		
		sharedStack.pop();
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return DROPD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command dropd doesn't take any arguments.");
		list.add("If stack is empty, error message occurs.");
		list.add("Dropd command takes dir path from top of stack and removes it.");
		list.add("Current dir doesn't change.");
		
		
        return Collections.unmodifiableList(list);
	}
}