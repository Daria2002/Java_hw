package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a parser of query statement. It gets query string through
 * constructor (actually, it gets everything user entered after query keyword). 
 * @author Daria Matković
 *
 */
public class QueryParser {

	/** everything user entered after query keyword **/
	private String queryString;
	/** lexer **/
	private QueryLexer lexer; 
	
	/**
	 * Constructor that initialize queryString.
	 * @param queryString everything user entered after query keyword. -
	 */
	public QueryParser(String queryString) {
		if(queryString == null) {
			throw new IllegalArgumentException("Query string is null");
		}
		this.queryString = queryString;
		this.lexer = new QueryLexer(queryString);
	}
	
	/**
	 * Checks if query was of of the form jmbag="xxx"
	 * @return true if query has form jmbag="xxx", otherwise false
	 */
	public boolean isDirectQuery() {
		this.lexer = new QueryLexer(queryString);
		lexer.nextToken();
		if(lexer.getToken().getType() != TokenQueryType.ATRIBUTE_NAME ||
				!"jmbag".equals(lexer.getToken().getValue())) {
			
			if(lexer.getToken().getValue().toString().contains("\"")) {
				throw new IllegalArgumentException("Atribute contains \"");
			}
			return false;
		}

		lexer.nextToken();
		if(lexer.getToken().getType() != TokenQueryType.OPERATOR ||
				!"=".equals(lexer.getToken().getValue())) {
			return false;
		}
		
		lexer.nextToken();
		if(lexer.getToken().getType() != TokenQueryType.STRING_LITERAL) {
			return false;
		}
		
		if(lexer.nextToken() != null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns jmbag, only for direct queries, otherwise throws exception
	 * @return string that represents queried jmbag value if query is direct one, 
	 * otherwise throws IllegalStateException
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Given query is not direct query");
		}
		
		String currentQuery = queryString.trim(); 
		return currentQuery.substring(currentQuery.indexOf("\"") + 1, 
				currentQuery.lastIndexOf("\""));
	}
	
	/**
	 * For all queries, this method must return a list of conditional expressions from query; 
	 * @return list of conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery() {
		this.lexer = new QueryLexer(queryString);
		List<ConditionalExpression> list = new ArrayList<>();
		
		String attribute;
		String operator;
		String stringLiteral;
		while(lexer.nextToken() != null) {
			attribute = lexer.getToken().getValue().toString();
			operator = lexer.nextToken().getValue().toString();
			stringLiteral = lexer.nextToken().getValue().toString();

			if(attribute.contains("\"")) {
				throw new IllegalArgumentException("Atribute contains \"");
			}
			
			if(attribute == null || operator == null || stringLiteral == null) {
				throw new IllegalArgumentException("Illegal query expression.");
			}
			
			list.add(new ConditionalExpression(getFieldValueGetter(attribute),
					stringLiteral, getComparisonOperator(operator)));
		}
		
		return list;
	}

	/**
	 * Returns field value for given attribute
	 * @param attribute given attribute
	 * @return field value
	 */
	private IFieldValueGetter getFieldValueGetter(String attribute) {
		switch (attribute) {
		case "lastName":
			return FieldValueGetters.LAST_NAME;

		case "firstName":
			return FieldValueGetters.FIRST_NAME;
	
		case "jmbag":
			return FieldValueGetters.JMBAG;
			
		default:
			throw new IllegalArgumentException("Unknown attribute.");
		}
	}
	
	/**
	 * Returns IComparisonOperator for given operator
	 * @param operator given operator
	 * @return IComparisonOperator for operator
	 */
	private IComparisonOperator getComparisonOperator(String operator) {
		switch (operator) {
		case "=":
			return ComparisonOperators.EQUALS;

		case "!=":
			return ComparisonOperators.NOT_EQUALS;
	
		case ">":
			return ComparisonOperators.GREATER;
			
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;

		case "<":
			return ComparisonOperators.LESS;
	
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		
		case "LIKE":
			return ComparisonOperators.LIKE;
			
		default:
			throw new IllegalArgumentException("Unknown operator.");
		}
	}
}
