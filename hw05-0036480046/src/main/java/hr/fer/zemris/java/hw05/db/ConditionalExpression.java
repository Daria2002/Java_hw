package hr.fer.zemris.java.hw05.db;

/**
 * Class that implements conditional expression. It has three class variables
 * that represent part of expression.
 * @author Daria Matković
 *
 */
public class ConditionalExpression {

	/** getter of specific field for some record **/
	IFieldValueGetter fieldGetter;
	/** string literal **/
	String stringLiteral;
	/** operator **/
	IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor that initialize getter, stringLiteral and operator
	 * @param getter given IFieldValueGetter object
	 * @param stringLiteral given string literal
	 * @param operator given operator
	 */
	public ConditionalExpression(IFieldValueGetter getter, String stringLiteral,
			IComparisonOperator operator) {
		this.fieldGetter = getter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = operator;
	}

	/**
	 * This method gets field value
	 * @return field value
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * This method gets string literal
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Gets operator for comparing values
	 * @return operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
