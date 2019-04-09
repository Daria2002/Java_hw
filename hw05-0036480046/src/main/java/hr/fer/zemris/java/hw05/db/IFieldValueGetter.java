package hr.fer.zemris.java.hw05.db;

/**
 * This program is used for obtaining a requested field value from given
 * StudentRecord
 * @author Daria Matković
 *
 */
public interface IFieldValueGetter {
	/**
	 * Gets requested field value
	 * @param record given record
	 * @return requested field value from given record
	 */
	public String get(StudentRecord record);
}
