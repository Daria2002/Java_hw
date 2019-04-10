package hr.fer.zemris.java.hw05.db;

/**
 * Interface that has one method for accepting records that pass filter
 * @author Daria Matković
 *
 */
public interface IFilter {
	/**
	 * Method accepts records that satisfied filter
	 * @param record given record
	 * @return true if record is accepted, otherwise false
	 */
	public boolean accepts(StudentRecord record);
}
