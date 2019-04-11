package hr.fer.zemris.java.hw05.db;

import java.awt.image.ImageFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements data for some student
 * @author Daria Matković
 *
 */
public class StudentRecord {
	/** grade **/
	private int grade;
	/** first name **/
	private String firstName;
	/** last name **/
	private String lastName;
	/** jmbag **/
	private String jmbag;
	
	/**
	 * Construcor that initialize data for student
	 * @param jmbag jmbag
	 * @param lastName last name
	 * @param firstName first name
	 * @param grade grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		if(grade > 5 || grade < 1) {
			throw new IllegalArgumentException("Grade out of range");
		}
		this.grade = grade;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jmbag = jmbag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.jmbag;
	}

	/**
	 * Gets grade
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * gets first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * gets last name
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * gets jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
}
