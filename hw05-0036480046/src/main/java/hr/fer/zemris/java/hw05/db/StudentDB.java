package hr.fer.zemris.java.hw05.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.lsystems.impl.Dictionary;

/**
 * This program reads the data from database.txt file in current directory.
 * If in provide file there are duplicate jmbags or if finalGrade is not a number
 * between 1 and 5, program should terminate with appropriate message to user.
 * @author Daria Matković
 *
 */
public class StudentDB {   

	private static StudentDatabase studentDatabase;
	
	/**
	 * Method that executes when program is run.
	 * @param args input from command line
	 */
	public static void main(String[] args) {
		// reads database.txt
		checkFile();
		
		Scanner getQuery = new Scanner(System.in);
		
		while (true) {
			System.out.print(">");
			
			String query = getQuery.nextLine();
			
			if("exit".equalsIgnoreCase(query)) {
				System.out.println("Goodbye!");
				getQuery.close();
				break;
			}
			
			if(!query.contains("query")) {
				continue;
			}
			
			query = query.replace("query", "");
			
			try {
				QueryParser parser = new QueryParser(query);

				List<StudentRecord> listStudentRecord = new ArrayList<StudentRecord>();
				if(parser.isDirectQuery()) {
					listStudentRecord.add(studentDatabase.forJMBAG(parser.getQueriedJMBAG()));
				} else {
					listStudentRecord = studentDatabase.filter(new QueryFilter(parser.getQuery()));
				}
				
				if(listStudentRecord.size() == 0) {
					System.out.println("No student found");
					continue;
				}
				
				printTable(listStudentRecord);
				
			} catch (Exception e) {
				System.out.println("Invalid query");
				continue;
			}
		}
	}
	
	private static void printTable(List<StudentRecord> record) {
		int col1 = 0;
		int col2 = 0;
		int col3 = 0;
		int col4 = 0;
		
		for(int i = 0; i < record.size(); i++) {
			if(record.get(i).getJmbag().length() > col1) {
				col1 = record.get(i).getJmbag().length();
			} 
			if(record.get(i).getLastName().length() > col2) {
				col2 = record.get(i).getLastName().length();
			}
			if(record.get(i).getFirstName().length() > col3) {
				col3 = record.get(i).getFirstName().length();
			} 
			if(String.valueOf(record.get(i).getGrade()).length() > col4) {
				col4 = String.valueOf(record.get(i).getGrade()).length();
			}
		}
		
		printFrame(col1, col2, col3, col4);
		System.out.println();

		for(int i = 0; i < record.size(); i++) {
			printData(col1, col2, col3, col4, record.get(i));
			System.out.println();
		}
		
		printFrame(col1, col2, col3, col4);
		System.out.println();
	}

	private static void printData(int col1, int col2, int col3, int col4,
			StudentRecord studentRecord) {
		System.out.print("| ");
		System.out.print(String.format("%-" + col1 + "s", studentRecord.getJmbag()));
		System.out.print(" | ");
		System.out.print(String.format("%-" + col2 + "s", studentRecord.getLastName()));
		System.out.print(" | ");
		System.out.print(String.format("%-" + col3 + "s", studentRecord.getFirstName()));
		System.out.print(" | ");
		System.out.print(String.format("%-" + col4 + "d", studentRecord.getGrade()));
		System.out.print(" |");
	}

	private static void printEqual(int length) {
		for(int i = 0; i < length; i++) {
			System.out.print("=");
		}
		System.out.print('+');
	}
	
	private static void printFrame(int col1, int col2, int col3, int col4) {
		System.out.print('+');
		printEqual(col1+2);
		printEqual(col2+2);
		printEqual(col3+2);
		printEqual(col4+2);
	}

	private static void checkFile() {
		File file = new File("src/main/java/hr/fer/zemris/java/hw05/db/database.txt");
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
		
		studentDatabase = new StudentDatabase(list);
	}
}
