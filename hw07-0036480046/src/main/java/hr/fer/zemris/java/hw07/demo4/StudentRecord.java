package hr.fer.zemris.java.hw07.demo4;

/**
 * This method represents data for one student.
 * @author Daria Matković
 *
 */
public class StudentRecord {
	/** student's jmbag **/
	public String jmbag;
	/** student's last name **/
	public String lastName;
	/** student's first name **/
	public String firstName;
	/** points that student got in first exam **/
	public double firstExam;
	/** points that student got in last exam **/
	public double lastExam;
	/** points that student got in laboratory exercise **/
	public double lab;
	/** student's grade **/
	public int grade;
	
	/**
	 * Constructor that initialize data for student
	 * @param jmbag jmabg
	 * @param lastName last name
	 * @param firstName first name
	 * @param d points from first exam
	 * @param e points from last exam
	 * @param f points from laboratory exercise
	 * @param grade student's grade
	 */
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
