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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents copy command and implements ShellCommand interface.
 * Copy command copies source file to destination file.
 * @author Daria Matković
 *
 */
public class MassrenameCommand implements ShellCommand {
	
	/** copy command name **/
	public final static String MASSRENAME_COMMAND = "massrename";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Please enter arguments");
			return ShellStatus.CONTINUE;
		}
		
		String[] data;
		// if cmd is filter, there should be 4 arguments
		if(arguments.contains("filter")) {
			data = CommandUtilityClass.checkArguments(arguments, 4);
			
			if(data != null) {
				return filterCommand(data, env);
			}
		
		// if cmd is groups, there should be 4 arguments
		} else if(arguments.contains("groups")) {
			data = CommandUtilityClass.checkArguments(arguments, 4);
			
			if(data != null) {
				
			}
			
		// if cmd is show there should be 5 arguments
		} else if(arguments.contains("show")) {
			data = CommandUtilityClass.checkArguments(arguments, 5);
			
			if(data != null) {
				
			}
		}
		
		env.writeln("Arguments are not valid.");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Filter command prints all files from dir1 selected with mask 
	 * @param data all arguments, each argument represents one element array
	 * @param env environment
	 * @return shellstatus 
	 */
	private ShellStatus filterCommand(String[] data, Environment env) {
		String sourceFilePath = CommandUtilityClass.resolvePath(data[0].trim(), env);

		// check if source path is path of existing path
		if(!new File(sourceFilePath).exists() || !new File(sourceFilePath).isDirectory()) {
			env.writeln("Source path need to be path to existing dir.");
			return ShellStatus.CONTINUE;
		}
		
		File sourceDir = new File(sourceFilePath);
		File[] filesInSourceDir = sourceDir.listFiles();
		
		Pattern pattern = Pattern.compile(data[4]);
		
		if(filesInSourceDir != null) {
			for(File file : filesInSourceDir) {
				Matcher m = pattern.matcher(file.toString());
				
				if(m.find()) {
					env.writeln(file.toString());
				}
			}
		}
		
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
		return MASSRENAME_COMMAND;
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