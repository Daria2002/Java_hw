package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This method takes dir path from stack and put it as current dir. If path to that dir
 * doesn't exists current directory doesn't change, but path is popped from stack.
 * @author Daria Matković
 *
 */
public class PopdCommand implements ShellCommand {

	/** cat command name **/
	public static final String POPD_COMMAND = "popd";
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
		
		env.setCurrentDirectory((Path)sharedStack.pop());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return POPD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command popd doesn't take any arguments.");
		list.add("It takes path from the top of stack, if path doesn't exist anymore current dir doesn't change.");
		list.add("If path exists, current dir changes to that value.");
		list.add("If stack is empty, command writes message and doesn't change stack or current dir path.");
		
		
        return Collections.unmodifiableList(list);
	}
}