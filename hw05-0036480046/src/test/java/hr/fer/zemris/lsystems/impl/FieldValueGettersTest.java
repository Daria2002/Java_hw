package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class FieldValueGettersTest {

	@Test
	void testSimple() {
		StudentRecord record = new StudentRecord("00000070", "Mouse", "Mickey", 5);
		assertEquals("Mickey", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Mouse", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("00000070", FieldValueGetters.JMBAG.get(record));
	}

	@Test
	void testDoubleLastName() {
		StudentRecord record = new StudentRecord("00000070", "Mouse Mous", "Mickey", 5);
		assertEquals("Mickey", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Mouse Mous", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("00000070", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	void testGradeHigh() {
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentRecord("00000070", "Mouse", "Mickey", 10);});
	}
	
	@Test
	void testGradeLow() {
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentRecord("00000070", "Mouse", "Mickey", -1);});
		
	}
}
