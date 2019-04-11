package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class QueryFilterTest {

	@Test
    public void test1() {
        String[] students = new String[] {new String("11  Prvi dva\tJedan 1"),
                new String("22  Drugi\tDva   2")};
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(students));
        QueryParser parser = new QueryParser(" jmbag < \"23\" AND lastName LIKE \"D*i\"");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));

        assertEquals(filteredRecords.size(), 1);
        assertEquals(filteredRecords.get(0).getFirstName(), "Dva");
        assertEquals(filteredRecords.get(0).getLastName(), "Drugi");
    }
	
	@Test
    public void test2() {
        String[] students = new String[] {new String("11  Prvi dva\tAAdan 1"),
                new String("22  Drugi\tDva   2")};
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(students));
        QueryParser parser = new QueryParser(" firstName < \"Ana\" AND lastName LIKE \"*\"");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));

        assertEquals(filteredRecords.size(), 1);
        assertEquals(filteredRecords.get(0).getFirstName(), "AAdan");
        assertEquals(filteredRecords.get(0).getLastName(), "Prvi dva");
    }
	
	@Test
    public void test3() {
        String[] students = new String[] {new String("11  Prvi dva\tAAdan 1"),
                new String("22  Drugi\tDva   2")};
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(students));
        QueryParser parser = new QueryParser("lastName LIKE \"*\"");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));

        assertEquals(filteredRecords.size(), 2);
    }
}
