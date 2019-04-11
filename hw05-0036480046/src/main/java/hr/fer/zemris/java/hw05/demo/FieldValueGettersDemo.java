package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demo program for field value getter
 * @author Daria Matković
 *
 */
public class FieldValueGettersDemo {
	
	/**
	 * Method that runs when program is run
	 * @param args no argumnets
	 */
	public static void main(String[] args) {
		
		StudentRecord record = new StudentRecord("00000070", "Mouse", "Mickey", 5);
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
}
