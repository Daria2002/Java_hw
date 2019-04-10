package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryFilter implements IFilter {

	private List<ConditionalExpression> list;
	
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		// TODO Auto-generated method stub
		return false;
	}

}
