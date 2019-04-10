package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryFilter implements IFilter {

	private List<ConditionalExpression> list;
	
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
