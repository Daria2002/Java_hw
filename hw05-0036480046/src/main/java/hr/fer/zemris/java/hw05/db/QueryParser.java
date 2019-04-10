package hr.fer.zemris.java.hw05.db;

import java.awt.List;

/**
 * This class represents a parser of query statement. It gets query string through
 * constructor (actually, it gets everything user entered after query keyword). 
 * @author Daria Matković
 *
 */
public class QueryParser {

	/** everything user entered after query keyword **/
	private String queryString;
	
	/**
	 * Constructor that initialize queryString.
	 * @param queryString everything user entered after query keyword. -
	 */
	public QueryParser(String queryString) {
		this.queryString = queryString;
	}
	
	/**
	 * Checks if query was of of the form jmbag="xxx"
	 * @return true if query has form jmbag="xxx", otherwise false
	 */
	public boolean isDirectQuery() {
		return true;
	}
	
	/**
	 * Returns jmbag, only for direct queries, otherwise throws exception
	 * @return string that represents queried jmbag value if query is direct one, 
	 * otherwise throws IllegalStateException
	 */
	public String getQueriedJBMAG() {
		return "";
	}
	
	/**
	 * For all queries, this method must return a list of conditional expressions from query; 
	 * @return list of conditional expressions from query
	 */
	/*public List<ConditionalExpression> getQuery() {
		//todo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}*/
	
	
}
