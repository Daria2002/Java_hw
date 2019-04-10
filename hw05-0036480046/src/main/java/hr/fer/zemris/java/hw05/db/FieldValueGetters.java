package hr.fer.zemris.java.hw05.db;

public class FieldValueGetters {
	
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		checkRecord(record);
		return record.getFirstName();
	};
	
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		checkRecord(record);
		return record.getLastName();
	};
	
	public static final IFieldValueGetter JMBAG = (record) -> {
		checkRecord(record);
		return record.getJmbag();
	};
	
	private static void checkRecord(StudentRecord record) {
		if(record == null) {
			throw new IllegalArgumentException("Given record is null");
		}
	}
}
