package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This method offers user to push current dir on stack and to set given directory
 * as new current dir. This command creates stack if it doesn't exist in map with key "cdstack".
 * Before changing current dir this command pushes current dir on stack.
 * @author Daria Matković
 *
 */
public class PushdCommand implements ShellCommand {
	/** cat command name **/
	public static final String PUSHD_COMMAND = "pushd";
	/** key for stack in shared data **/
	public static final String STACK_KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Insert one argument");
			return ShellStatus.CONTINUE;
		}
		
		String[] args = CommandUtilityClass.checkArguments(arguments, 1);
		
		if(args.length != 1) {
			env.writeln("Arguments are not valid");
			return ShellStatus.CONTINUE;
		}
		
		Path newCurrentDir = Paths.get(CommandUtilityClass.resolvePath(args[0].toString(), env));
		File file = new File(newCurrentDir.toString());
		
		// is given path is not existing dir, nothing changes
		if(!file.isDirectory() || !file.exists()) {
			env.writeln("Directory with given path doesn't exists.");
			return ShellStatus.CONTINUE;
		}
		
		Stack sharedStack = (Stack) env.getSharedData(STACK_KEY);
		
		// create stack if it doesn't exists
		if(sharedStack == null) {
			sharedStack = new Stack();
			env.setSharedData(STACK_KEY, sharedStack);
		}
		
		sharedStack.add(env.getCurrentDirectory());
		env.setCurrentDirectory(newCurrentDir);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return PUSHD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("This method takes one argument - path to new current dir.");
		list.add("Before changing current dir this command push current dir on stack in shared data.");
		list.add("If stack doesn't exists it creates stack.");
		
        return Collections.unmodifiableList(list);
	}
}