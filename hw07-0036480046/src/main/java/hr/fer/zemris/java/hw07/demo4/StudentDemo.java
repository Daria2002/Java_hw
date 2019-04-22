package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StudentDemo {
	public static void main(String[] args) {
		Path path = Paths.get(System.getProperty("user.dir") + 
				"/src/main/java/hr/fer/zemris/java/hw07/demo4/studenti.txt");
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		try {
			List<String> lines = Files.readAllLines(path);
			records = convert(lines);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long broj = records.stream()
				.filter(record -> record.firstExam + record.lastExam + record.lab > 25)
				.count();
		
		System.out.println(broj);
		
		long broj5 = records.stream()
				.filter(record -> record.grade == 5)
				.count();
		
		System.out.println(broj5);
		
		List<StudentRecord> odlikasi = records.stream()
				.filter(record -> record.grade == 5).collect(Collectors.toList());
		
		for(StudentRecord odlikas:odlikasi) {
			System.out.print(odlikas.toString());
		}
		
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(record -> record.grade == 5)
				.sorted((o1, o2) -> Double.compare(
						o2.firstExam + o2.lastExam + o2.lab,
						o1.firstExam + o1.lastExam + o1.lab))
				.collect(Collectors.toList());
		
		for(StudentRecord odlikas:odlikasiSortirano) {
			System.out.print(odlikas.toString());
		}
		
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(record -> record.grade == 1)
				.sorted((o1, o2) -> Integer.compare(Integer.parseInt(o1.jmbag),
						Integer.parseInt(o2.jmbag))).map(x -> x.jmbag)
						.collect(Collectors.toList());
		
		for(String odlikas:nepolozeniJMBAGovi) {
			System.out.println(odlikas);
		}
		
		//Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> studentList = new ArrayList<StudentRecord>();
		
		for(String line:lines) {
			if(line.isBlank() || line.isEmpty()) {
				continue;
			}
			
			String[] lineArray = line.split("\\s+");
			
			studentList.add(new StudentRecord(lineArray[0], lineArray[1],
					lineArray[2], Double.parseDouble(lineArray[3]),
					Double.parseDouble(lineArray[4]), Double.parseDouble(lineArray[5]), 
					Integer.parseInt(lineArray[6])));
					
		}
		
		return studentList;
	}
}
