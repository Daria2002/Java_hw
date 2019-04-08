package hr.fer.zemris.java.hw05.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import hr.fer.zemris.lsystems.impl.Dictionary;

public class StudentDB {   
	
	public static void main(String[] args) {
		File file = new File("src/main/java/hr/fer/zemris/java/hw05/db/database.txt");
		FileInputStream fstream;
		Dictionary<String, String> dictionary = new Dictionary<>();
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null)   {
				String[] array = strLine.trim().split("\\s+");
				
				if(Integer.parseInt(array[array.length-1]) < 1 ||
						Integer.parseInt(array[array.length-1]) > 5 ||
						dictionary.get(array[0]) != null) {
					throw new IllegalArgumentException("Invallid data in file.");
				}
				
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < array.length; i++) {
					builder.append(array[i] + " ");
				}
				
				dictionary.put(array[0], builder.toString());
			}
			
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Can't open file.");
		}
	}
}
