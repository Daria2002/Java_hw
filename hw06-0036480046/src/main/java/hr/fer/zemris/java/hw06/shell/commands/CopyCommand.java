package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents copy command and implements ShellCommand interface.
 * Copy command copies source file to destination file.
 * @author Daria Matković
 *
 */
public class CopyCommand implements ShellCommand {

	public final static String COPY_COMMAND = "copy";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COPY_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("The copy command expects two arguments: source file and destination file.");
		list.add("Is destination file exists, you should ask user is it allowed to overwrite it.");
		list.add("Copy command works only with files.");
		list.add("If 2nd arg is dir, copies orig file into that dir using the orig file name.");
		
        return Collections.unmodifiableList(list);
	}
}