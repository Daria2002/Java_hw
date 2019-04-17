package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents hexdump command that produces hex-output for given file name.
 * @author Daria Matković
 *
 */
public class HexdumpCommand implements ShellCommand {

	/** hexdump command name **/
	public final static String HEXDUMP_COMMAND = "hexdump";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] argsArray;
		if(arguments.trim().contains("\"")) {
			argsArray = arguments.split("\"");
			
			if(argsArray.length != 2 || (!argsArray[0].isBlank() && !argsArray[0].isEmpty())) {
				
				for(int i = 0; i < argsArray.length; i++) {
					System.out.println(argsArray[i]);
				}
				
				System.out.println("Insert only one argument");
				return ShellStatus.CONTINUE;
			}
			
			arguments = argsArray[1];
		}
		
		FileInputStream inputStream;
		
		try{
    	    inputStream = new FileInputStream(arguments);
 
    	    byte[] buffer = new byte[16];
 
    	    int index = 0;
    	    int length;
    	    
    	    while ((length = inputStream.read(buffer)) > 0){
    	    	
    	    	String hex = makeHex(index);
    	    	System.out.print(("00000000" + hex + ":").substring(hex.length()));
    	    	
    	    	// array contains elements that whose bytes value is range from 32 to 127
    	    	String[] fixedText = new String[16];
    	    	
    	    	
    	    	for(int i = 0; i < length; i++) {
    	    		
    	    		if(i == 7) {
    	    			System.out.printf(" %02x", buffer[i]);
    	    			System.out.print("|");
    	    			
    	    		} else if(i == 8) {
    	    			System.out.printf("%02x ", buffer[i]);
    	    			
    	    		} else {
        	    		System.out.printf(" %02x ", buffer[i]);
    	    		}
    	    		
    	    		
    	    		if(buffer[i] >= 32 && buffer[i] <= 127) {
    	    			fixedText[i] = String.valueOf((char)buffer[i]);
    	    		} else {
    	    			fixedText[i] = ".";
    	    		}
    	    		
    	    	}
    	    	
    	    	
    	    	for(int i = 0; i < 16 - length; i++) {
    	    		System.out.print("    ");
    	    	}
    	    	
    	    	for(int i = 0; i < fixedText.length; i++) {
    	    		if(fixedText[i] == null) {
    	    			break;
    	    		}
    	    		
    	    		if(i == 0) {
    	    			System.out.print("|");
    	    		}
    	    		
    	    		System.out.printf(" " + fixedText[i] + " ");
    	    	}
    	    	
    	    	System.out.println();
    	    	
    	    	buffer = new byte[16];
    	    	//hexdump /home/daria/Desktop/test2/filetest
    	    	
    	    	index += 16;
    	    }

    	    inputStream.close();
 
    	}catch(IOException ioe){
    		System.out.println("Error occurred while executing hexdump command.");
    	}
		
		
		return ShellStatus.CONTINUE;
	}

	private String makeHex(int dec) {
		String x = Integer.toHexString(dec);
		return x;
	}

	@Override
	public String getCommandName() {
		return HEXDUMP_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List list = new ArrayList();
		
		list.add("Takes one argument: file name, and produces hex-output.");
		list.add("Bytes whose value is less than 32 or greater than 127 are printed."); 
		list.add(" otherwise '.' is printed instead of char.");
		list.add("Printed message says what current symbol value is.");
		
        return Collections.unmodifiableList(list);
	}
}