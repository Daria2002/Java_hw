package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class implements interface IFilter and gets List of conditional expressions
 * @author daria
 *
 */
public class QueryFilter implements IFilter {
	/** List of conditional expressions **/
	private List<ConditionalExpression> list;
	
	/**
	 * Constructor that initialize list of conditional expressions 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		ConditionalExpression expression;
		
		for(int i = 0; i < list.size(); i++) {
			expression = list.get(i);
			if(!expression.getComparisonOperator()
			.satisfied(expression.getFieldGetter().get(record), 
					expression.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}

}
