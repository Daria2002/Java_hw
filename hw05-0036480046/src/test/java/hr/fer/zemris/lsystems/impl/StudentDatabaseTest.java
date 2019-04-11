package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.IFilter;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class StudentDatabaseTest {

	private final static int NUMBER_OF_RECORDS = 63;
	private final static int EMPTY = 0;
	
	private static class returnFalse implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
	
	private static class returnTrue implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
		
	}
	
	private List<String> getList() {
		List<String> list = new ArrayList<>();
		
		File file = new File("src/main/java/hr/fer/zemris/java/hw05/db/database.txt");
		FileInputStream fstream;
		Dictionary<String, String> dictionary = new Dictionary<>();
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				list.add(strLine.trim());
			}
			
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Can't open file.");
		}
		
		return list;
	}
	
	@Test
	void testTrue() {
		
		StudentDatabase studentDatabase = new StudentDatabase(getList());
		List<StudentRecord> students = studentDatabase.filter(new returnTrue());
		
		assertEquals(NUMBER_OF_RECORDS, students.size());
	}

	@Test
	void testFalse() {
		StudentDatabase studentDatabase = new StudentDatabase(getList());
		List<StudentRecord> students = studentDatabase.filter(new returnFalse());
		
		assertEquals(EMPTY, students.size());
	}
	
	@Test
	void testForJMBAG() {
		StudentDatabase studentDatabase = new StudentDatabase(getList());
		StudentRecord student = studentDatabase.forJMBAG("0000000001");
		
		assertEquals("Akšamović", student.getLastName());
		assertEquals("Marin", student.getFirstName());
		assertEquals(2, student.getGrade());
	}
}
