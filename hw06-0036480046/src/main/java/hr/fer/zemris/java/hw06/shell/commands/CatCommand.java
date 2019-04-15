package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command represents cat command that opens given file and write its content
 * to console. This class implements ShellComand interface.
 * @author Daria Matković
 *
 */
public class CatCommand implements ShellCommand {

	/** cat command name **/
	public static final String CAT_COMMAND = "cat";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		File file = new File(arguments);
		FileInputStream fstream;
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				env.writeln(strLine);
			}
			
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Can't open file.");
			System.exit(0);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CAT_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset should be used.");
		list.add("This command opens given file and writes its content to console.");
		
        return Collections.unmodifiableList(list);
	}
}