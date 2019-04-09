package hr.fer.zemris.java.hw05.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import hr.fer.zemris.lsystems.impl.Dictionary;

/**
 * This program reads the data from database.txt file in current directory.
 * If in provide file there are duplicate jmbags or if finalGrade is not a number
 * between 1 and 5, program should terminate with appropriate message to user.
 * @author Daria Matković
 *
 */
public class StudentDB {   
	
	/**
	 * Method that executes when program is run.
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		File file = new File("src/main/java/hr/fer/zemris/java/hw05/db/database.txt");
		FileInputStream fstream;
		Dictionary<String, String> dictionary = new Dictionary<>();
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				String[] array = strLine.trim().split("\\s+");
				
				// last elements in row is grade, if grade is out of range, throw
				// exception
				if(Integer.parseInt(array[array.length-1]) < 1 ||
						Integer.parseInt(array[array.length-1]) > 5 ||
						dictionary.get(array[0]) != null) {
					throw new IllegalArgumentException("Invallid data in file.");
				}
				
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < array.length; i++) {
					builder.append(array[i] + " ");
				}
				
				// key in dictionary is first element in row, value is whole row
				dictionary.put(array[0], builder.toString());
			}
			
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Can't open file.");
		}
	}
}
