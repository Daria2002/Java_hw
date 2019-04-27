package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	/** copy command name **/
	public final static String COPY_COMMAND = "copy";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Copy command takes two arguments (dest and src path)."
					+ " Src path must be file and dest can be file or dir");
			return ShellStatus.CONTINUE;
		}
		
		String[] data = CommandUtilityClass.checkTwoArguments(arguments, 2);
		
		if(data.length != 2) {
			env.writeln("Copy command takes two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		String sourceFilePath = data[0].trim();
		
		// check if source path is path of existing path
		if(!new File(sourceFilePath).exists() || !new File(sourceFilePath).isFile()) {
			env.writeln("Source path need to be path to existing file.");
			return ShellStatus.CONTINUE;
		}
		
		String destinationFilePath = data[1].trim();
		File destFile = new File(destinationFilePath);
		Path dir = Paths.get(destinationFilePath);
		
		// build destination file's name if given path to destination is path to dir
		if(destFile.isDirectory()) {
			String[] helpArray = sourceFilePath.split("/");
			String sourceFileName = helpArray[helpArray.length-1];
			
			dir = dir.resolve(sourceFileName);
			destFile = new File(dir.toString());
		}
		
		// overwriting occurs only when destination path is file and it already exists or
		// when dest path is path to dir and file named as source exists in dest folder
		if(destFile.exists()) {
			env.writeln("Do you want to overwrite destination file? y/n");
			
			if("n".equals(env.readLine())) {
				return ShellStatus.CONTINUE;
			}
		}
		
		copyFileToFile(sourceFilePath, destFile.toString());
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method copies source file content to destination file content
	 * @param sourceFilePath source file path
	 * @param destinationFilePath destination file path
	 */
	private void copyFileToFile(String sourceFilePath, String destinationFilePath) {
		
		FileInputStream inputStream;
		FileOutputStream outputStream;
		
		try{
    	    inputStream = new FileInputStream(sourceFilePath);
    	    outputStream = new FileOutputStream(destinationFilePath);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    while ((length = inputStream.read(buffer)) > 0){
    	    	outputStream.write(buffer, 0, length);
    	    }

    	    inputStream.close();
    	    outputStream.close();
 
    	}catch(IOException ioe){
    		System.out.println(destinationFilePath);
    		System.out.println("Copy not possible");
    		return;
    	}
	}
	
	@Override
	public String getCommandName() {
		return COPY_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("The copy command expects two arguments: source file and destination file.");
		list.add("If destination file exists, you should ask user is it allowed to overwrite it.");
		list.add("Copy command works only with files.");
		list.add("If 2nd arg is dir, copies orig file into that dir using the orig file name.");
		
        return Collections.unmodifiableList(list);
	}
}