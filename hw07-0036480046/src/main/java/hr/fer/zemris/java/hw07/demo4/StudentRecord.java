package hr.fer.zemris.java.hw07.demo4;

public class StudentRecord {
	
	public String jmbag;
	public String lastName;
	public String firstName;
	public double firstExam;
	public double lastExam;
	public double lab;
	public int grade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, double d, double e, double f,
			int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.firstExam = d;
		this.lastExam = e;
		this.lab = f;
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		String student = jmbag + "\t" + lastName + "\t" + firstName + "\t" +
	firstExam + "\t" + lastExam + "\t" + lab + "\t" + grade + "\n";
		return student.toString();
	}
}
