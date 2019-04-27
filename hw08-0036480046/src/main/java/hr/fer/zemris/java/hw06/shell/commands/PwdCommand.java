package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command represents pwd command that prints in terminal current directory.
 * Pwd command gets no arguments. Printed current directory prints absolute path.
 * @author Daria Matković
 *
 */
public class PwdCommand implements ShellCommand {

	/** pwd command name **/
	public static final String PWD_COMMAND = "pwd";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments != null) {
			env.writeln("Pwd command gets no argument.");
			return ShellStatus.CONTINUE;
		}

		env.writeln(env.getCurrentDirectory().toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return PWD_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command pwd takes no arguments.");
		list.add("Prints absolute path to current directory.");
		
        return Collections.unmodifiableList(list);
	}
}