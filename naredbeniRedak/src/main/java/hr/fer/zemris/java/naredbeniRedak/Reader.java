package hr.fer.zemris.java.naredbeniRedak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This program reads the data from database.txt file in current directory.
 * If in provide file there are duplicate jmbags or if finalGrade is not a number
 * between 1 and 5, program should terminate with appropriate message to user.
 * @author Daria Matković
 *
 */
public class Reader {   
	
	/**
	 * Method that executes when program is run.
	 * @param args input from command line
	 */
	public static void main(String[] args) {
		Scanner getInput = new Scanner(System.in);
		
		while (true) {

			System.out.print("> ");
			
			String input = getInput.nextLine();
			if("exit".equalsIgnoreCase(input)) {
				System.out.println("Goodbye!");
				getInput.close();
				break;
			}
			
			try {
				
				//File file = new File("src/main/java/hr/fer/zemris/java/hw05/db/database.txt");
				
				String trenutniPaket = "src/main/java/hr/fer/zemris/java/naredbeniRedak/";
				
				String path = trenutniPaket + input;
				
				String fileString = getStringOutOfFile(Paths.get(path));
				//System.out.println(fileString);
				
				List<String> fileList = getListOutOfFile(new File(path));
				
				for(String value:fileList) {
					System.out.println(value);
				}
				
			} catch (Exception e) {
				System.out.println("Invalid query");
				continue;
			}
		}
	}
	
	/**
	 * Get list out of file
	 * @return
	 */
	private static String getStringOutOfFile(Path path) {
		try {
			return Files.readString(path);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Get list out of file
	 * @return
	 */
	private static List<String> getListOutOfFile(File file) {
		FileInputStream fstream;
		List<String> list = new ArrayList<String>();
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				list.add(strLine);
			}
			
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Can't open file.");
			System.exit(0);
		}
		return list;
	}
	
	/**
	 * For input "123abc", the method above will return 123.

		For "abc1000def", 1000.
		
		For "555abc45", 555.
		
		For "abc", will return an empty string.
	 * @param str
	 * @return
	 */
	public static String getFirstNumber(final String str) {                

	    if(str == null || str.isEmpty()) return "";

	    StringBuilder sb = new StringBuilder();
	    boolean found = false;
	    for(char c : str.toCharArray()){
	        if(Character.isDigit(c)){
	            sb.append(c);
	            found = true;
	        } else if(found){
	            // If we already found a digit before and this char is not a digit, stop looping
	            break;                
	        }
	    }

	    return sb.toString();
	}
	
	private static String[] split(String string, String regex) {
		string = string.replace(" ", "\\s+");
		
		return string.split(regex);
	}
	
	/**
	 * 1234a6789:false
	   123456789:true
	 * @param string
	 * @return
	 */
	private static boolean numbersOnly(String string) {
		    return string.matches("[0-9]+");
	}
}
