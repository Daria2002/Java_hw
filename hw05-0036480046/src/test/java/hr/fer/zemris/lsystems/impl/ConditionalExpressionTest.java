package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class ConditionalExpressionTest {

	@Test
	void testLIKE() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE
		);
		StudentRecord record = new StudentRecord("00000080", "BosMouse", "Mickey", 1);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		assertTrue(recordSatisfies);
	}
	
	@Test
	void testLIKE2() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME, "Bos*se", ComparisonOperators.LIKE
		);
		StudentRecord record = new StudentRecord("00000080", "BosMouse", "Mickey", 1);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		assertTrue(recordSatisfies);
	}

	@Test
	void testGREATERJmbag() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.JMBAG, "2", ComparisonOperators.GREATER
		);
		StudentRecord record = new StudentRecord("5", "BosMouse", "Mickey", 1);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		assertTrue(recordSatisfies);
	}
	
	@Test
	void testLESSJmbag() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.JMBAG, "2", ComparisonOperators.LESS
		);
		StudentRecord record = new StudentRecord("5", "BosMouse", "Mickey", 1);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		assertFalse(recordSatisfies);
	}
}
