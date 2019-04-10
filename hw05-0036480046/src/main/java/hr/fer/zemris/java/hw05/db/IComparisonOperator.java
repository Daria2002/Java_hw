package hr.fer.zemris.java.hw05.db;

/**
 * Interface for operators for comparing two string values.
 * @author Daria Matković
 *
 */
public interface IComparisonOperator {
	/**
	 * Method compare two strings with comparison operator
	 * @param value1 given value for comparison
	 * @param value2 given value for comparison
	 * @return true if strings satisfied expression, otherwise false
	 */
	public boolean satisfied(String value1, String value2);
}
