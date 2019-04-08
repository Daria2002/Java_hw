package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Map;

public class StudentDatabase {
	
	private List<StudentRecord> studentRecordList;
	private Map<String, StudentRecord> studentMap;
	
	public StudentDatabase(List<String> studentList) {
		for(int i = 0; i < studentList.size(); i++) {
			String[] array = studentList.get(i).trim().split("\\s+");
			
			try {
				if(Integer.parseInt(array[array.length-1]) < 1 ||
						Integer.parseInt(array[array.length-1]) > 5 ||
						studentMap.get(array[0]) != null) {
					throw new IllegalArgumentException("Invallid data in file.");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Invallid data in file.");
			}
			
			
			String lastName = "";
			for(int k = 1; k < array.length-2; k++) {
				lastName += array[i];
			}
			
			this.studentRecordList.add(new StudentRecord(array[0], lastName,
					array[array.length-2], Integer.parseInt(array[array.length-1])));
			
			studentMap.put(array[0], this.studentRecordList.get(i));
		}
	}
	/*
	public StudentRecord forJMBAG(String jmbag) {
		
	}
	
	public List<StudentRecord> filter(IFilter filter) {
		
	}*/
}
