package hr.fer.zemris.java.hw05.db;

/**
 * This class implements getter for field values.
 * @author Daria Matković
 *
 */
public class FieldValueGetters {
	
	/**
	 * This class implements IFieldValueGetter and returns first name field
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		checkRecord(record);
		return record.getFirstName();
	};
	
	/**
	 * This class implements IFieldValueGetter and returns last name field
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		checkRecord(record);
		return record.getLastName();
	};
	
	/**
	 * This class implements IFieldValueGetter and returns jmbag field
	 */
	public static final IFieldValueGetter JMBAG = (record) -> {
		checkRecord(record);
		return record.getJmbag();
	};
	
	/**
	 * This method throws exception if given record is null
	 * @param record given record
	 */
	private static void checkRecord(StudentRecord record) {
		if(record == null) {
			throw new IllegalArgumentException("Given record is null");
		}
	}
}
