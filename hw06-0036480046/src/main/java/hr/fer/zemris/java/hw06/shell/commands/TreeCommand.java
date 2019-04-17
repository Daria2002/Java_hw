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
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		File directory = new File(arguments);
		if(directory.isFile()) {
			return ShellStatus.CONTINUE;
		}
		
        try {

    		Path startPath = Paths.get(arguments);
    		
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            	int n = 2;
                @Override
                public FileVisitResult preVisitDirectory(Path dir,
                        BasicFileAttributes attrs) {
                    System.out.println(dir.toString());
                    n = n + 2;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String s = String.format("%1$" + n + "s", "");
                    System.out.println(s + file.toString());    
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
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