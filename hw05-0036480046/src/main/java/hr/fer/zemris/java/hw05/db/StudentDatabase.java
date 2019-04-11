package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements student database. 
 * @author Daria Matković
 *
 */
public class StudentDatabase {
	/** List for getting List of records **/
	private List<StudentRecord> studentRecordList = new ArrayList<>();
	/** mapping student jmbag to student record **/
	private Map<String, StudentRecord> studentMap = new HashMap<String, StudentRecord>();
	
	/**
	 * Constructor that initialize database and checks conditions for grade range
	 * and unique jmbag condition
	 * @param studentList list with data to save in database
	 */
	public StudentDatabase(List<String> studentList) {
	       
        if(studentList == null) {
            throw new IllegalArgumentException("Student database is null.");
        }
       
        for(int i = 0; i < studentList.size(); i++) {
            String[] array = studentList.get(i).trim().split("\\s+");
           
            // skip if row is empty
            if(array.length == 0) {
                continue;
            }
           
            // checking conditions for jmbags and grades
            int grade = Integer.parseInt(array[array.length-1]);
            String jmbag = array[0];
           
            try {
                if(grade < 1 || grade > 5 || studentMap.get(jmbag) != null) {
                    throw new IllegalArgumentException("Invallid data in file.");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invallid data in file.");
            }
           
            String[] helpArray = studentList.get(i).trim().split("\t");
            String lastName = "";
            String firstName = "";
            if (helpArray.length == 1)
            {
                lastName = array[1];
                firstName = array[2];
            } else if(helpArray.length == 2) {
               
                lastName = getLastName(helpArray[0], 1);
                firstName = getFirstName(helpArray[1], 1);
            } else if (helpArray.length == 4) {
                lastName = getLastName(helpArray[1], 0);
                firstName = getFirstName(helpArray[2], 0);
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
           
            StudentRecord record = new StudentRecord(jmbag, lastName, firstName, grade);
            studentRecordList.add(record);
            studentMap.put(jmbag, record);
        }
    }

	/**
	 * Gets first name
	 * @param string query
	 * @param offset start index
	 * @return first name
	 */
	private String getFirstName(String string, int offset) {
        String[] array = string.trim().split(" ");
       
        String value = "";
        for(int i = 0; i < array.length - offset; i++) {
            if(array[i].isEmpty()) {
                continue;
            }
           
            value += array[i];
           
            if(i != array.length - 1 - offset) {
                value += " ";
            }
        }
        return value.isEmpty() ? array[0] : value.trim();
    }
 
	/**
	 * Gets last name
	 * @param string query
	 * @param startIndex start index
	 * @return last name
	 */
    private String getLastName(String string, int startIndex) {
        String[] array = string.trim().split(" ");
       
        String value = "";
        for(int i = startIndex; i < array.length; i++) {
            if(array[i].isEmpty()) {
                continue;
            }
           
            value += array[i];
           
            if(i != array.length - 1) {
                value += " ";
            }
        }
       
        return value.isEmpty() ? array[0] : value.trim();
    }
    
	/**
	 * Returns student record for given jmbag
	 * @param jmbag given jmbag
	 * @return student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentMap.get(jmbag);
	}
	
	/**
	 * Returns list of filtered records
	 * @param filter filter for records
	 * @return List of filtered records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		
		List<StudentRecord> tempList = new ArrayList<>();
		
		for(int i = 0; i < studentRecordList.size(); i++) {
			if(filter.accepts(studentRecordList.get(i))) {
				tempList.add(studentRecordList.get(i));
			}
		}
		
		return tempList;
	}
}
