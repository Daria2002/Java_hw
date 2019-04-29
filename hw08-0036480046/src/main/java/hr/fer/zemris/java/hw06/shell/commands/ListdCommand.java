package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This method prints all paths saved on stack.
 * @author Daria Matković
 *
 */
public class ListdCommand implements ShellCommand {

	/** cat command name **/
	public static final String LISTD_COMMAND = "listd";
	/** key for stack in shared data **/
	public static final String STACK_KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments != null) {
			env.writeln("Command listd doesn't take any arguments");
			return ShellStatus.CONTINUE;
		}
		
		Stack sharedStack = (Stack) env.getSharedData(STACK_KEY);
		
		// is stack doesn't exist or stack is empty
		if(sharedStack == null || sharedStack.isEmpty()) {
			env.writeln("There is no path on stack");
			return ShellStatus.CONTINUE;
		}
		
		Iterator stackValues = sharedStack.iterator();
		
		while(stackValues.hasNext()) {
			env.writeln(stackValues.next().toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return LISTD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("This method takes no arguments.");
		list.add("It prints all paths saved on stack");
		
        return Collections.unmodifiableList(list);
	}
}