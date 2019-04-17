package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;

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
		
		String[] data = CommandUtilityClass.checkTwoArguments(arguments);
		
		if(data == null) {
			return ShellStatus.CONTINUE;
		}
		
		String sourceFilePath = data[0].trim();
		String destinationFilePath = data[1].trim();
		
		File destFile = new File(destinationFilePath);
		
		if(destFile.exists()) {
			System.out.println("Do you want to overwrite destination file? y/n");
			
			if("n".equals(env.readLine())) {
				return ShellStatus.CONTINUE;
			}
		}
		
		// if second arg is dir copy first file to dir
		if(destFile.isDirectory()) {
			copyFileToDir(sourceFilePath, destinationFilePath);
			
		} else {
			copyFileToFile(sourceFilePath, destinationFilePath);
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
    		return;
    	}
	}
	
	/**
	 * This method creates new file named as source file to destination folder
	 * @param sourceFilePath that needs to be copied in destination folder
	 * @param destinationFolderPath folder where source file needs to be copied
	 */
	private void copyFileToDir(String sourceFilePath, String destinationFolderPath) {
		
		String[] helpArray = sourceFilePath.split("/");
		String sourceFileName = helpArray[helpArray.length-1];
		
		String destinationFile = destinationFolderPath + "/" + sourceFileName;
		File file = new File(destinationFile);

        // If file doesn't exists, then create it
        if (!file.exists()) {
            try {
				file.createNewFile();
			} catch (IOException e) {
				return;
			}
        }
		
        try {
			Path source = Paths.get(sourceFilePath);
			BufferedInputStream in = new BufferedInputStream(Files.newInputStream(source),
					1000);

			BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(
					Paths.get(destinationFile)), 1000);

	        byte[] inputBytes = new byte[1000];
	        
	        // read is number of read elements
			for(int read = in.read(inputBytes); read >= 0; read = in.read(inputBytes)) {
				out.write(inputBytes, 0, read);
			}
			
	        out.close();
		    in.close();
			
		} catch (Exception e) {
			return;
		}
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