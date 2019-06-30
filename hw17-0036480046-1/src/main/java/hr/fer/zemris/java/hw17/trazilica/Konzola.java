package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.derby.shared.common.util.ArrayUtil;
import org.apache.derby.tools.sysinfo;

public class Konzola {

	private static final String QUERY_COMMAND = "query";
	private static String[] stopWords = null;
	static Set<String> vocabulary = new TreeSet<String>();
	private static int numberOfFiles = 0;
	
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
		
//		for(String val : vocabulary) {
//			System.out.println(val);
//		}
		
		for(int i = 0; i < queryWords.length; i++) {
			if(inVocabulary(queryWords[i])) {
				if(counter != 0) {
					sb.append(", ");
				}
				sb.append(queryWords[i]);
				counter++;
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
		System.out.println("Najboljih 10 rezultata:");
		
		Set<String> bestResults = getBestResults();
		
		for(String result : bestResults) {
			
		}
	}
	
	private static Set<String> getBestResults(String[] queryWords) {
		Set<String> vocabulary = new TreeSet<String>();
		List<String> bestResults = new ArrayList<String>();
		
		List<Double> results = new ArrayList<Double>();
		
		// .../hw17-0036480046-1
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
		
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
		
		Double[] vdi = calculateVQuery(queryWords.toString());
		Double vdiNorm = norm(vdi);
		
		for (File file : filesInFolder) { //For each of the entries do:
	        calculateSim(vdi, vdiNorm, file);
	    }
		
		return vocabulary;
	}
	
	private static double calculateSim(Double[] vdi, Double vdiNorm, File file) {
		Double[] vdj = vdj(file);
		return (dot(vdi,vdj))*(vdiNorm*norm(vdj));
	}

	private static Double[] vdj(File file) {
		try {
			return calculateVQuery(getStringFromFile(file.getPath().toString()));
		} catch (Exception e) {
			System.out.println("Error while calculating vdj");
		}
		return null;
	}

	private static double dot(Double[] vdi, Double[] vdj) {
		double result = 0;
		for(int i = 0; i < vocabulary.size(); i++) {
			result += vdi[i] * vdj[i];
		}
		return result;
	}

	private static Double norm(Double[] vQuery) {
		double norm = 0;
		for(int i = 0; i < vQuery.length; i++) {
			norm += Math.pow(vQuery[i], 2);
		}
		return Math.sqrt(norm);
	}

	private static Double[] calculateVQuery(String wordsToAnalyse) {
		Double[] tfidf = new Double[vocabulary.size()];
		int i = 0;
		for(String word:vocabulary) {
			tfidf[i] = tfidf(word, wordsToAnalyse);
		}
		return tfidf;
	}

	private static double tfidf(String word, String documentText) {
		double tfidf = tf(word, documentText) * idf(word);
		return tfidf;
	}

	private static double idf(String word) {
		return Math.log10(numberOfFiles/numberOfDocumentsContainingWord(word));
	}
	// getStringFromFile(document.getPath().toString()).trim()
	private static int tf(String word, String documentText) {
		try {
			return countOccurrences(documentText, word);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static boolean inVocabulary(String word) {
		return vocabulary.contains(word);
	}
	
	private static int numberOfDocumentsContainingWord(String word) {
		int numberOfOccurrances = 0;
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
        
		String fileText;
		
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
	    for (File file : filesInFolder) { //For each of the entries do:
	        if (!file.isDirectory()) { //check that it's not a dir
	        	try {
					fileText = getStringFromFile(file.getPath().toString()).trim();
					if(containsWord(word, fileText)) {
						numberOfOccurrances++;
					}
					
				} catch (Exception e) {
					System.out.println("Can't read file.");
					System.out.println("Given path: " +
					file.getPath().toString());
					e.printStackTrace();
				}
	        }
	    }
	    return numberOfOccurrances;
	}
	
	private static boolean containsWord(String word, String fileText) {
		int lastIndex = 0;
		
		while(lastIndex != -1){

		    lastIndex = fileText.indexOf(word,lastIndex);

		    if(lastIndex != -1){
		    	return true;
		    }
		}
		return false;
	}

	private static int countOccurrences(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = str.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findStr.length();
		    }
		}
		return count;
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
	        	// 
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
