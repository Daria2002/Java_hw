package hr.fer.zemris.java.hw05.db;

import java.awt.image.ImageFilter;
import java.util.ArrayList;
import java.util.List;

public class StudentRecord {
	
	private int grade;
	private String firstName;
	private String lastName;
	private String jmbag;
	
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
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
}
