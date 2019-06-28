package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Konzola {

	private static final String QUERY_COMMAND = "query";
	private static String[] stopWords = null;
	static Set<String> vocabulary = new TreeSet<String>();
	
	public static void main(String[] args) {
		
		String projectPath = System.getProperty("user.dir");
		File fileWithStopWords = new File(projectPath + "/src/main/resources/hrvatski_stoprijeci.txt");

		try {
			stopWords = getStopWords(fileWithStopWords);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		vocabulary = makeVocabulary();
		
		while(true) {
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter command > ");
			
			String command = scanner.nextLine();
			System.out.println("command: " + command);
			
			String[] commandWords = command.split(" ");
			
			if(QUERY_COMMAND.equals(commandWords[0])) {
				query(Arrays.copyOfRange(commandWords, 1, commandWords.length));
			}
	
		}
		
	}
	
	private static void query(String[] queryWords) {
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");
		int counter = 0;
		for(int i = 0; i < queryWords.length; i++) {
			if(inVocabulary(queryWords[i])) {
				if(counter % 2 == 1) {
					sb.append(queryWords[i]);
				}
				counter++;
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
		System.out.println("Najboljih 10 rezultata:");
		String[] bestResults = getBestResults();
		
		for(int i = 0; i < bestResults.length; i++) {
			System.out.println(bestResults[i]);
		}
	}

	private static String[] getBestResults() {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean inVocabulary(String string) {
		return Arrays.asList(vocabulary).contains(string);
	}

	private static Set<String> makeVocabulary() {
		Set<String> vocabulary = new TreeSet<String>();
		// .../hw17-0036480046-1
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
        
		String fileText;
		String[] textArray;
		
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
	    for (File file : filesInFolder) { //For each of the entries do:
	        if (!file.isDirectory()) { //check that it's not a dir
	        	try {
					fileText = getStringFromFile(file.getPath().toString()).trim();
					textArray = fileText.trim().split(" ");
					
					for(int i = 0; i < textArray.length; i++) {
						if(!stopWord(textArray[i])) {
							
							String line = textArray[i];
							
							line = line.replaceAll("\\.", "");
							line = line.replaceAll(",", "");
							line = line.replaceAll("\\;", "");
							line = line.replaceAll("\\:", "");
							line = line.replaceAll("\\!", "");
							line = line.replaceAll("\\?", "");
							line = line.replaceAll("\"", "");
							line = line.replaceAll("»", " ");
					    	line = line.replaceAll("«", " ");
					    	line = line.replaceAll("\\(", "");
					    	line = line.replaceAll("\\)", "");
					    	line = line.replaceAll("\\s+", "");
					    	
							if(line.isBlank() || line.isEmpty()) {
								continue;
							}
							
							vocabulary.add(line);
						}
					}
					
				} catch (Exception e) {
					System.out.println("Can't read file.");
					System.out.println("Given path: " +
					file.getPath().toString());
					e.printStackTrace();
				}
	        }
	    }
		
		return vocabulary;
	}
	
	private static boolean stopWord(String string) throws FileNotFoundException, IOException {
		for(int i = 0; i < stopWords.length; i++) {
			if(string.equals(stopWords[i])) {
				return true;
			}
		}
		
		return false;
	}

	private static String[] getStopWords(File fileWithStopWords) throws FileNotFoundException, IOException {
		ArrayList<String> result = new ArrayList<>();
		 
		try (BufferedReader br = new BufferedReader(new FileReader(fileWithStopWords))) {
		    while (br.ready()) {
		    	String line = br.readLine();
		        result.add(line.trim());
		    }
		}
		
		String[] stopWords = new String[result.size()];
		stopWords = result.toArray(stopWords);
		return stopWords;
	}

	public static String getStringFromFile(String filePath) throws Exception {
	    File fl = new File(filePath);
	    FileInputStream fin = new FileInputStream(fl);
	    String ret = convertStreamToString(fin);
	    //Make sure you close all streams.
	    fin.close();        
	    return ret;
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}
}
