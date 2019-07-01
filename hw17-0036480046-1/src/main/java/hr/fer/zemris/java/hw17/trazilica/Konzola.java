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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	private static Map<String, Double> idfVector = new HashMap<String, Double>();
	private static Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
	
	
	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		File fileWithStopWords = new File(projectPath + "/src/main/resources/hrvatski_stoprijeci.txt");

		try {
			stopWords = getStopWords(fileWithStopWords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		vocabulary = makeVocabularyAndInitializeWordFrequency();
		
		idfVector = idfVector();
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
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
		
		File[] filesInFolder = dir.listFiles();
		
		Double[] vdi = calculateVQuery(Arrays.toString(queryWords));
		
		Double sim[] = new Double[filesInFolder.length];
		int i = 0;
		for (File file : filesInFolder) { //For each of the entries do:
	        sim[i++] = calculateSim(vdi, file);
	    }
		
		bestResultsIndexes = maxKIndex(sim, 10);
		i = -1;
		System.out.println("best indexes:"+bestResultsIndexes[0]);
		System.out.println("best inde best res:"+sim[bestResultsIndexes[0]]);
		while(i < 9 && sim[bestResultsIndexes[++i]] != 0) {
			System.out.println("best indexes:"+bestResultsIndexes[i]);
			System.out.println("best inde best res:"+sim[bestResultsIndexes[i]]);
			bestResults.add("[ " + i + "] (" + sim[bestResultsIndexes[i]] + ") " + 
		filesInFolder[bestResultsIndexes[i]].getPath());
		}
		
		return bestResults;
	}
	
	public static int[] maxKIndex(Double[] array, int k) {
		Double[] copyArray = Arrays.copyOf(array, array.length);
		Arrays.sort(copyArray, Collections.reverseOrder());
		
		Double[] biggestValues = new Double[k];
		for (int i = 0; i < k; i++) {
			biggestValues[i] = copyArray[i]; 
		}
		
		int[] indexes = new int[k];
		int index = 0;
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < biggestValues.length; j++) {
				if(Math.abs(biggestValues[j]-array[i]) < 0.0001) {
					indexes[index++] = i;
					break;
				}
			}
		}
		return indexes;
	}
	
	private static double calculateSim(Double[] vdi, File file) {
		Double[] vdj = vdj(file);
		Double vdiNorm = norm(vdi);
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
		for(int i = 0; i < vdi.length; i++) {
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
			//System.out.println(word);
			Double x = tfidf(word, wordsToAnalyse);
			if(Double.isNaN(x)) {
				System.out.println("ovo je nan");
			}
			tfidf[i++] = tfidf(word, wordsToAnalyse);
		}
		return tfidf;
	}

	private static double tfidf(String word, String documentText) {
		if(Double.isNaN(idfVector.get(word))) {
			System.out.println("idf je nan");
		}
		if(Double.isNaN(tf(word, documentText))) {
			System.out.println("tf je nan");
		}
		double tfidf = tf(word, documentText) * idfVector.get(word);
		
		if(Double.isNaN(tfidf)) {
			System.out.println("tf:"+tf(word, documentText));
			System.out.println("idf:"+idfVector.get(word));
			System.out.println("tfidf je nan u funk");
		}
		return tfidf;
	}

	private static Map<String, Double> idfVector() {
		Map<String, Double> map = new HashMap<String, Double>();
		for(String word : vocabulary) {
			if(Double.isInfinite(idf(word))) {
				System.out.println("word:"+word);
				System.out.println("wordfreq:"+wordFrequency.get(word));
				System.out.println("idf od rijeci je inf");
			}
			map.put(word, idf(word));
		}
		return map;
	}
	
	private static double idf(String word) {
		return Math.log10(numberOfFiles/wordFrequency.get(word));
	}
	
	// getStringFromFile(document.getPath().toString()).trim()
	private static int tf(String word, String documentText) {
		try {
			if(Double.isNaN(countOccurrences(documentText, word))) {
				System.out.println("Count occurrances is wrong");
			}
			return countOccurrences(documentText, word);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static boolean inVocabulary(String word) {
		return vocabulary.contains(word);
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
	
	private static Set<String> makeVocabularyAndInitializeWordFrequency() {
		Set<String> vocabulary = new HashSet<String>();
		// .../hw17-0036480046-1
		String projectPath = System.getProperty("user.dir");
		File dir = new File(projectPath + "/src/main/resources/clanci");
        
		String fileText;
		File[] filesInFolder = dir.listFiles(); // This returns all the folders and files in your path
		int helpCount;
		numberOfFiles = filesInFolder.length;
		
		for (File file : filesInFolder) { //For each of the entries do:
			
			// words that already occurred in file
			List<String> occurredWords = new ArrayList<String>();
			
	        if (!file.isDirectory()) { //check that it's not a dir
	        	try {
					fileText = readFile(file.getPath().toString());
					
					String[] lineArray = null;
					fileText = fileText.toLowerCase();
					
					lineArray = fileText.split("\\P{L}+");
					for(String element : lineArray) {
						if(element.isEmpty() || element.isBlank() || stopWord(element)) {
							continue;
						}
						//System.out.println(element);
						
						if(occurredWords.contains(element)) {
							continue;
						}
						
						occurredWords.add(element);
						
						// if element is not added in map
						if(wordFrequency.get(element) == null) {
							wordFrequency.put(element, 1);
						} else {
							helpCount = wordFrequency.get(element);
							wordFrequency.put(element, helpCount+1);
						}
						
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
