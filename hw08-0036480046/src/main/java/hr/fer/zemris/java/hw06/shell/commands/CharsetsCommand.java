package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents charsets command that list names of supported charsets
 * for user's Java platform. This class implements ShellCommand interface.
 * @author Daria Matković
 *
 */
public class CharsetsCommand implements ShellCommand{

	/** charsets command name **/
	public final static String CHARSETS_COMMAND = "charsets";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		// charsets take no argument
		if(arguments != null) {
			env.writeln("Charsets command doesn't take arguments");
			return null;
		}
		
		for (String str : Charset.availableCharsets().keySet()) {
		      env.writeln(str);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CHARSETS_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command charsets takes no arguments and lists names of supported charsets.");
		list.add("A single charset name is written per line.");
		
        return Collections.unmodifiableList(list);
	}
}
