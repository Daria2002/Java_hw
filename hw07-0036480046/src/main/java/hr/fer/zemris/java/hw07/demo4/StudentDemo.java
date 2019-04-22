package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StudentDemo {
	public static void main(String[] args) {
		Path path = Paths.get("./studenti.txt");
		try {
			List<String> lines = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//List<StudentRecord> records = convert(lines);
	}
}
