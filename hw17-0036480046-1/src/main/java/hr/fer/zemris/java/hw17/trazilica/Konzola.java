package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.derby.tools.sysinfo;
public class Konzola {

	private static final String QUERY_COMMAND = "query";
	private static final String TYPE_COMMAND = "type";
	private static final String EXIT_COMMAND = "exit";
	private static final String RESULTS_COMMAND = "results";
	private static String[] stopWords = null;
	static Set<String> vocabulary = new TreeSet<String>();
	private static int numberOfFiles = 0;
	private static List<String> bestResults = null;
	static int[] bestResultsIndexes = null;
	
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
			} else if(RESULTS_COMMAND.equals(commandWords[0]) && commandWords.length == 1) {
				results();
			} else if(EXIT_COMMAND.equals(commandWords[0]) && commandWords.length == 1) {
				System.exit(0);
			} else if(TYPE_COMMAND.equals(commandWords[0]) && commandWords.length == 2) {
				type(Integer.valueOf(commandWords[1]));
			} else {
				System.out.println("Nepoznata naredba.");
			}	
		}
	}
	
	private static void type(int resultIndex) {
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
		int fileIndex = bestResultsIndexes[resultIndex];
        File fileToPrint = dir.listFiles()[fileIndex];
		
        try {
			System.out.println(readFile(fileToPrint.getPath().toString()));
		} catch (Exception e) {
			System.out.println("Not possible to print file");
		}
	}

	private static void results() {
		if(bestResults != null) {
			for(int i = 0; i < bestResults.size(); i++) {
				System.out.println(bestResults.get(i));
			}
			return;
		}
		System.out.println("Prije naredbe results nije pozvana naredba query");
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
		
		bestResults = getBestResults(queryWords);
		for(String result : bestResults) {
			System.out.println(result);
		}
	}
	
	private static List<String> getBestResults(String[] queryWords) {
		List<String> bestResults = new ArrayList<String>();
		
		// .../hw17-0036480046-1
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
		
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
		
		Double[] vdi = calculateVQuery(Arrays.toString(queryWords));
		Double vdiNorm = norm(vdi);
		
		Double sim[] = new Double[filesInFolder.length];
		int i = 0;
		for (File file : filesInFolder) { //For each of the entries do:
	        sim[i++] = calculateSim(vdi, vdiNorm, file);
	    }
		
		bestResultsIndexes = indexesOfTopElements(sim, 10);
		i = -1;
		while(sim[bestResultsIndexes[++i]] != 0) {
			bestResults.add("[ " + i + "] (" + sim[bestResultsIndexes[i]] + ") " + 
		filesInFolder[bestResultsIndexes[i]].getPath());
		}
		
		return bestResults;
	}
	
	static int[] indexesOfTopElements(Double[] orig, int numberOfIndexes) {
        Double[] copy = Arrays.copyOf(orig,orig.length);
        Arrays.sort(copy);
        Double[] honey = Arrays.copyOfRange(copy,copy.length - numberOfIndexes, copy.length);
        int[] result = new int[numberOfIndexes];
        int resultPos = 0;
        for(int i = 0; i < orig.length; i++) {
            double onTrial = orig[i];
            int index = Arrays.binarySearch(honey,onTrial);
            if(index < 0) continue;
            result[resultPos++] = i;
        }
        return result;
    }
	
	private static double calculateSim(Double[] vdi, Double vdiNorm, File file) {
		Double[] vdj = vdj(file);
		return (dot(vdi,vdj))*(vdiNorm*norm(vdj));
	}

	private static Double[] vdj(File file) {
		try {
			return calculateVQuery(readFile(file.getPath().toString()));
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
		for(String word : vocabulary) {
			System.out.println(word);
			tfidf[i++] = tfidf(word, wordsToAnalyse);
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
					fileText = readFile(file.getPath().toString()).trim();
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
		if(fileText.contains(word)) {
			return true;
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
		Set<String> vocabulary = new HashSet<String>();
		// .../hw17-0036480046-1
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
        
		String fileText;
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
	    
		for (File file : filesInFolder) { //For each of the entries do:
	        if (!file.isDirectory()) { //check that it's not a dir
	        	try {
					fileText = readFile(file.getPath().toString());
					
					numberOfFiles++;
					String[] lineArray = fileText.toLowerCase().split("\\P{L}+");
					for(String element : lineArray) {
						if(element.isEmpty() || element.isBlank() || stopWord(element)) {
							continue;
						}
						System.out.println(element);
						vocabulary.add(element);
					}
					
				} catch (Exception e) {
					System.out.println("Can't read file.");
					System.out.println("Given path: " +
					file.getPath().toString());
					e.printStackTrace();
				}
	        }
	    }
		System.out.println("vel rje="+vocabulary.size());
		return vocabulary;
	}

	private static boolean stopWord(String string) throws FileNotFoundException, IOException {
		for(int i = 0; i < stopWords.length; i++) {
			if(string.equals(stopWords[i].toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}

	private static String[] getStopWords(File fileWithStopWords) 
			throws FileNotFoundException, IOException {
		return readFile(fileWithStopWords.getPath().toString()).split("\n");
	}

	static String readFile(String path) throws IOException {
	  return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	}
}
