package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class FieldValueGettersDemo {
	
	public static void main(String[] args) {
		
		StudentRecord record = new StudentRecord("00000070", "Mouse", "Mickey", 5);
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
}
