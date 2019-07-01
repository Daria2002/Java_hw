package hr.fer.zemris.java.hw17.trazilica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class Konzola {

	private static final String QUERY_COMMAND = "query";
	private static final String TYPE_COMMAND = "type";
	private static final String EXIT_COMMAND = "exit";
	private static final String RESULTS_COMMAND = "results";
	private static String[] stopWords = null;
	static Set<String> vocabulary = new HashSet<String>();
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
		
		for(int i = 0; i < queryWords.length; i++) {
			if(vocabulary.contains(queryWords[i])) {
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
		Double[] vdi = calculateVQuery(Arrays.toString(queryWords).toLowerCase());
		
		Double sim[] = new Double[filesInFolder.length];
		int i = 0;
		for (File file : filesInFolder) { //For each of the entries do:
	        sim[i++] = calculateSim(vdi, file);
	    }
		
		bestResultsIndexes = maxKIndex(sim, 10);
		i = -1;
		while(i < 9 && bestResultsIndexes[i+1] != -1 &&
				sim[bestResultsIndexes[++i]] != 0) {
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
		
		for(int j = 0; j < biggestValues.length; j++) {
			for(int i = 0; i < array.length; i++) {
				if(Double.isNaN(biggestValues[j])) {
					indexes[index++] = -1;
					break;
				} else if(Math.abs(biggestValues[j]-array[i]) < 0.000000001) {
					indexes[index++] = i;
					break;
				}
			}
		}
		return indexes;
	}
	
	private static double calculateSim(Double[] vdi, File file) {
		Double[] vdj = null;
		try {
			vdj = calculateVQuery(readFile(file.getPath().toString()).toLowerCase());
		} catch (IOException e) {
			System.out.println("error calculating vdj");
		}
		return (dot(vdi,vdj))/(norm(vdi)*norm(vdj));
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
			tfidf[i++] = countOccurrences(word, wordsToAnalyse) * idfVector.get(word);
		}
		return tfidf;
	}

	private static Map<String, Double> idfVector() {
		Map<String, Double> map = new HashMap<String, Double>();
		for(String word : vocabulary) {
			map.put(word, Math.log10(numberOfFiles/wordFrequency.get(word)));
		}
		return map;
	}
	
	private static int countOccurrences(String word, String txt) {
		int M = word.length();         
        int N = txt.length();         
        int res = 0; 
  
        for (int i = 0; i <= N - M; i++) { 
            int j;             
            for (j = 0; j < M; j++) { 
                if (txt.charAt(i + j) != word.charAt(j)) { 
                    break; 
                } 
            } 
            
            if (j == M && !Character.isAlphabetic(txt.charAt(i+M)) && 
            		(i == 0 || !Character.isAlphabetic(txt.charAt(i-1)))) {
                res++;                 
                j = 0;                 
            }             
        }      
        return res;         
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
					fileText = readFile(file.getPath().toString()).toLowerCase();
					
					String[] lineArray = null;
					
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
