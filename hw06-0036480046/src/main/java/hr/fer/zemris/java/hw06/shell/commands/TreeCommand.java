package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
	public final static int START_INDENT = 0;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		File directory = new File(arguments);
		if(directory.isFile()) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			listStructure(START_INDENT, directory);
		} catch (IOException e) {
			System.out.println("Error occured, so command didn't execute.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	static void listStructure(int indent, File file) throws IOException {
	    for (int i = 0; i < indent; i++)
	      System.out.print(' ');
	    
	    System.out.println(file.getName());
	    
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
		List list = new ArrayList();
		
		list.add("The tree command expects a single argument: directory name.");
		list.add("Command prints a tree (each dir level shifts output two spaces to the right).");
		
        return Collections.unmodifiableList(list);
	}
}