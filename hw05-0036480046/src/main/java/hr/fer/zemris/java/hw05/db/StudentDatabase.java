package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	
	private List<StudentRecord> studentRecordList = new ArrayList<>();
	private Map<String, StudentRecord> studentMap = new HashMap<String, StudentRecord>();
	
	public StudentDatabase(List<String> studentList) {
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
			
			String lastName = "";
			String firstName = "";
			
			if(array.length > 4) {
				String[] helpArray = studentList.get(i).trim().split("\t");
				
				lastName = getLastName(helpArray[0]);
				firstName = getFirstName(helpArray[1]);
				
			} else {
				lastName = array[1];
				firstName = array[2];
			}
			
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, grade);
			studentRecordList.add(record);
			studentMap.put(jmbag, record);
		}
	}

	private String getFirstName(String string) {
		String[] array = string.trim().split(" ");
		return array[0];
	}

	private String getLastName(String string) {
		String[] array = string.trim().split(" ");
		return array[array.length-1];
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return studentMap.get(jmbag);
	}
	
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
