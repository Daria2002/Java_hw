package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class implements tree command. Tree command prints a tree for given directory.
 * @author Daria Matković
 *
 */
public class TreeCommand implements ShellCommand {

	/** tree command name **/
	public final static String TREE_COMMAND = "tree";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		arguments = CommandUtilityClass.checkOneArgument(arguments);
		
		if(arguments == null) {
			env.writeln("Insert only one argument");
			return ShellStatus.CONTINUE;
		}
		
		File directory = new File(arguments);
		if(!directory.isDirectory()) {
			env.writeln("Please enter one argument - path to dir");
			return ShellStatus.CONTINUE;
		}
		
		int startIndent = 0;
		try {
			listStructure(startIndent, directory);
		} catch (IOException e) {
			env.writeln("Error occured, so command didn't execute.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * This method lists structure of given path, structure prints with given indent
	 * @param indent given indent
	 * @param file file whose structure needs to be printed
	 * @throws IOException throws exception if error occurs
	 */
	static void listStructure(int indent, File file) throws IOException {
		// add indent
	    for (int i = 0; i < indent; i++) {
		      System.out.print(' ');
	    }
	    
	    // current directory name 
	    System.out.println(file.getName());
	    
	    // if current element is directory list structure of directory, otherwise continue
	    if (file.isDirectory()) {
	      File[] files = file.listFiles();
	      
	      for (int i = 0; i < files.length; i++)
	        listStructure(indent + 2, files[i]);
	    }
    }
	
	@Override
	public String getCommandName() {
		return TREE_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("The tree command expects a single argument: directory name.");
		list.add("Command prints a tree (each dir level shifts output two spaces to the right).");
		
        return Collections.unmodifiableList(list);
	}
}