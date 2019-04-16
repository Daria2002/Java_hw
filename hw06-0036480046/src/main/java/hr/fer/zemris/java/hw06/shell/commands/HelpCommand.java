package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents help command that prints command name and description of
 * selected command.
 * @author Daria Matković
 *
 */
public class HelpCommand implements ShellCommand {

	/** help command name **/
	public final static String HELP_COMMAND = "help";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			for (String key : env.commands().keySet()) {
				env.writeln(env.commands().get(key).getCommandName());
			}
			
			return ShellStatus.CONTINUE;
		} 
		
		try {
			List<String> list = env.commands().get(arguments).getCommandDescription();
			
			for(int i = 0; i < list.size(); i++) {
				env.writeln(list.get(i));
			}
			
		} catch (Exception e) {
			env.writeln("Command \"" + arguments + "\" doesn't exists.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return HELP_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("If there are no arguments, it lists names of supported commands.");
		list.add("If there is one argument, it prints name and the description of selected command");
		
        return Collections.unmodifiableList(list);
	}
}