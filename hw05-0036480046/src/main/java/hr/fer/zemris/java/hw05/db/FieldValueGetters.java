package hr.fer.zemris.java.hw05.db;

public class FieldValueGetters {
	
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		return record.getFirstName();
	};
	
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		return record.getLastName();
	};
	
	public static final IFieldValueGetter JMBAG = (record) -> {
		return record.getJmbag();
	};
}
