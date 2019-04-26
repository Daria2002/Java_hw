package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
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
		
		arguments = CommandUtilityClass.checkOneArgument(arguments);
		
		if(arguments == null) {
			env.writeln("Insert only one argument");
			return ShellStatus.CONTINUE;
		}
		
		FileInputStream inputStream;
		
		try{
    	    inputStream = new FileInputStream(arguments);
 
    	    byte[] buffer = new byte[16];
 
    	    int index = 0;
    	    int length;
    	    
    	    while ((length = inputStream.read(buffer)) > 0){
    	    	
    	    	String hex = makeHex(index);
    	    	env.write(("00000000" + hex + ":").substring(hex.length()));
    	    	
    	    	// array contains elements that whose bytes value is range from 32 to 127
    	    	String[] fixedText = new String[16];
    	    	
    	    	
    	    	for(int i = 0; i < length; i++) {
    	    		// special case when adding values next to first |
    	    		// no space between 7th hex value and | on the right
    	    		if(i == 7) {
    	    			env.write(String.format(" %02x", buffer[i]));
    	    			env.write("|");
    	    		
    	    		// no space between 8th hex value and | on the left
    	    		} else if(i == 8) {
    	    			env.write(String.format("%02x ", buffer[i]));
    	    		
    	    		// adding rest of elements, there is space on left and right
    	    		} else {
        	    		env.write(String.format(" %02x ", buffer[i]));
    	    		}
    	    		
    	    		
    	    		if(buffer[i] >= 32 && buffer[i] <= 127) {
    	    			fixedText[i] = String.valueOf((char)buffer[i]);
    	    		} else {
    	    			fixedText[i] = ".";
    	    		}
    	    		
    	    	}
    	    	
    	    	// adding spaces if whole row is not filled with hex values
    	    	for(int i = 0; i < 16 - length; i++) {
    	    		// if filled before first |
    	    		if(length <= 8 && i == 15-length) {
    	    			env.write("   ");
    	    			break;
    	    		}
    	    		
    	    		env.write("    ");
    	    	}
    	    	
    	    	// print text on right
    	    	for(int i = 0; i < fixedText.length; i++) {
    	    		if(fixedText[i] == null) {
    	    			break;
    	    		}
    	    		
    	    		if(i == 0) {
    	    			env.write("|");
    	    		}
    	    		
    	    		env.write(fixedText[i]);
    	    	}
    	    	
    	    	env.writeln("");
    	    	
    	    	buffer = new byte[16];
    	    	index += 16;
    	    }

    	    inputStream.close();
 
    	}catch(IOException ioe){
    		env.writeln("Error occurred while executing hexdump command.");
    	}
		
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method calculate hex value of given decimal number
	 * @param dec decimal number to convert
	 * @return hexadecimal value
	 */
	private String makeHex(int dec) {
		return Integer.toHexString(dec);
	}

	@Override
	public String getCommandName() {
		return HEXDUMP_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Takes one argument: file name, and produces hex-output.");
		list.add("Bytes whose value is less than 32 or greater than 127 are printed."); 
		list.add(" otherwise '.' is printed instead of char.");
		list.add("Printed message says what current symbol value is.");
		
        return Collections.unmodifiableList(list);
	}
}