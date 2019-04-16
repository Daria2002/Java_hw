package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
		
		String[] argsArray = arguments.split(" ");
		
		if(argsArray.length != 2) {
			return ShellStatus.CONTINUE;
		}
		
		String sourceFilePath = argsArray[0];
		String[] helpArray = argsArray[0].split("/");
		String sourceFileName = helpArray[helpArray.length-1];
		
		String destinationFilePath = argsArray[1];
		
		// if second arg is dir copy first file to dir
		if(new File(destinationFilePath).isDirectory()) {
			copyFileToDir(sourceFilePath, destinationFilePath, helpArray);
			
		} else {
			copyFileToFile(sourceFilePath, destinationFilePath);
		}
		
		return ShellStatus.CONTINUE;
	}

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
    		return;
    	}
	}

	private void copyFileToDir(String sourceFilePath, String destinationFilePath, String[] helpArray) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCommandName() {
		return COPY_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("The copy command expects two arguments: source file and destination file.");
		list.add("If destination file exists, you should ask user is it allowed to overwrite it.");
		list.add("Copy command works only with files.");
		list.add("If 2nd arg is dir, copies orig file into that dir using the orig file name.");
		
        return Collections.unmodifiableList(list);
	}
}