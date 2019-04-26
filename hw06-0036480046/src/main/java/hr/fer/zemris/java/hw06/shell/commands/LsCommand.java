package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents ls command that writes directory listing in four columns.
 * First column indicates if currents object is directory (d), (readable) r, (writable) w, (executable) e 
 * Second column contains object size in bytes
 * Third column is file creation date/time
 * Forth column is file name
 * @author Daria Matković
 *
 */
public class LsCommand implements ShellCommand {

	/** ls command name **/
	public final static String LS_COMMAND = "ls";
	public final static int SIZE_IN_BYTES = 10;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		arguments = CommandUtilityClass.checkOneArgument(arguments);
		
		if(arguments == null) {
			env.writeln("Insert only one argument");
			return ShellStatus.CONTINUE;
		}

		File dir = new File(arguments);
		File[] filesInDir = dir.listFiles();
		
		// given argument should be dir
		if(dir.isFile() || filesInDir == null) {
			env.writeln("Command ls takes one argument - path to dir.");
			return ShellStatus.CONTINUE;
		}
		
		for(File file : filesInDir) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Path path = Paths.get(file.toString());
			
			BasicFileAttributeView faView = Files.getFileAttributeView(
			path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
			);
			BasicFileAttributes attributes;
			
			try {
				attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				env.write((file.isDirectory() ? "d" : "-") + (file.canRead() ? "r" : "-")
						+ (file.canWrite() ? "w" : "-") + (file.canExecute() ? "x" : "-") + " ");
				
				env.write(String.format("%1$" + SIZE_IN_BYTES + "s", Files.size(path) + " "));
				
				env.write(formattedDateTime + " ");

				env.writeln(file.getName());
				
			} catch (IOException e) {
				env.writeln("Error occured");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return LS_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command ls takes one argument – directory.");
		list.add("Command prints all files and folders in directory with details.");
		
        return Collections.unmodifiableList(list);
	}
}