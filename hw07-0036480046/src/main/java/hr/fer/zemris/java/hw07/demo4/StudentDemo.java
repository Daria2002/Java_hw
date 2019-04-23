package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
		
		long broj = vratiBodovaViseOd25(records);
		printFrame(1);
		System.out.println(broj);
		
		long broj5 = vratiBrojOdlikasa(records);
		printFrame(2);
		System.out.println(broj5);
		
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		printFrame(3);
		for(StudentRecord odlikas:odlikasi) {
			System.out.print(odlikas.toString());
		}
		
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		printFrame(4);
		for(StudentRecord odlikas:odlikasiSortirano) {
			System.out.print(odlikas.toString());
		}
		
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		printFrame(5);
		for(String jmbag:nepolozeniJMBAGovi) {
			System.out.println(jmbag);
		}
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		printFrame(6);
		for(Integer ocjena:mapaPoOcjenama.keySet()) {
			System.out.println("Ocjena " + ocjena + ":" + mapaPoOcjenama.get(ocjena));
		}
		
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		printFrame(7);
		for(Integer ocjena:mapaPoOcjenama2.keySet()) {
			System.out.println("Ocjena " + ocjena + ":" + mapaPoOcjenama2.get(ocjena));
		}
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		printFrame(8);
		for(Boolean stanje:prolazNeprolaz.keySet()) {
			System.out.println((stanje == false ? "neprolaz:" : "prolaz:") + prolazNeprolaz.get(stanje));
		}
	}

	private static void printFrame(int taskNumber) {
		if(taskNumber != 1) {
			System.out.println();
		}
		System.out.println("Zadatak " + taskNumber);
		System.out.println("=========");
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
	
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(record -> record.firstExam + record.lastExam + record.lab > 25)
				.count();
	}
	
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(record -> record.grade == 5)
				.count();
	}
	
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(record -> record.grade == 5).collect(Collectors.toList());
	}
	
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(record -> record.grade == 5)
				.sorted((o1, o2) -> Double.compare(
						o2.firstExam + o2.lastExam + o2.lab,
						o1.firstExam + o1.lastExam + o1.lab))
				.collect(Collectors.toList());
	}
	
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(record -> record.grade == 1)
				.sorted((o1, o2) -> Integer.compare(Integer.parseInt(o1.jmbag),
						Integer.parseInt(o2.jmbag))).map(x -> x.jmbag)
						.collect(Collectors.toList());
	}
	
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(student -> student.grade));
	}

	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(student -> student.grade, 
		        s -> 1, 
		        (left, right) -> left + right));
	}
	
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(student -> student.grade > 1));
	}
}
