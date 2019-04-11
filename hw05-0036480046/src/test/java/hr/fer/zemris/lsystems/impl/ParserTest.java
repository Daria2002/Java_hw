package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryLexer;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class ParserTest {

	@Test
	void testException() {
		QueryParser newParser = new QueryParser("jmbag LIKE \"ja*va\" ");
		assertThrows(IllegalStateException.class, () -> {newParser.getQueriedJMBAG();});
	}
	
	@Test
    public void isDirectQueryTest() {
        QueryParser newParser = new QueryParser("firstName<=\"Jasna\"");
        assertEquals(newParser.isDirectQuery(), false);
       
        newParser = new QueryParser(" jmbag = \"0036480046\"");
        assertEquals(newParser.isDirectQuery(), true);
    }
	
	@Test
	public void bigTest() {
		/*
		QueryLexer lexer = new QueryLexer("firstName = \"pero\" anD jmbag LIKE \"008784\"");
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken());*/
		
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(2, qp2.getQuery().size());
	}
}
