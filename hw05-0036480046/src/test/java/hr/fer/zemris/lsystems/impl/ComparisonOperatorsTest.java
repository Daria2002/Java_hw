package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

class ComparisonOperatorsTest {

	@Test
	void testLIKE() {
		IComparisonOperator oper = ComparisonOperators.LIKE;	
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	void testEQUALS() {
		IComparisonOperator x = ComparisonOperators.EQUALS;
		assertTrue(x.satisfied("Ana", "Ana"));
		assertFalse(x.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testGREATER() {
		IComparisonOperator x = ComparisonOperators.GREATER;
		assertTrue(x.satisfied("Žana", "Ana"));
		assertFalse(x.satisfied("Žana", "Žana"));
		assertFalse(x.satisfied("Ana", "Žana"));
	}
	
	@Test
	void testGREATEROREQUALS() {
		IComparisonOperator x = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(x.satisfied("Žana", "Ana"));
		assertTrue(x.satisfied("Žana", "Žana"));
		assertFalse(x.satisfied("Ana", "Žana"));
	}

	@Test
	void testLESS() {
		IComparisonOperator x = ComparisonOperators.LESS;
		assertFalse(x.satisfied("Žana", "Ana"));
		assertFalse(x.satisfied("Žana", "Žana"));
		assertTrue(x.satisfied("Ana", "Žana"));
	}
	
	@Test
	void testLESSOREQUALS() {
		IComparisonOperator x = ComparisonOperators.LESS_OR_EQUALS;
		assertFalse(x.satisfied("Žana", "Ana"));
		assertTrue(x.satisfied("Žana", "Žana"));
		assertTrue(x.satisfied("Ana", "Žana"));
	}
	
	@Test
	void testNOTEQUALS() {
		IComparisonOperator x = ComparisonOperators.NOT_EQUALS;
		assertTrue(x.satisfied("Jasna", "Ana"));
		assertFalse(x.satisfied("Ana", "Ana"));
	}
}
