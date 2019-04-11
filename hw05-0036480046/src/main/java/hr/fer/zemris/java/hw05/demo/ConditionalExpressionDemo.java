package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demo program for conditional expression
 * @author Daria Matković
 *
 */
public class ConditionalExpressionDemo {

	/**
	 * Program that executes when program is run
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE
		);
		StudentRecord record = new StudentRecord("00000080", "BosMouse", "Mickey", 1);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		System.out.println(recordSatisfies);
	}
}
