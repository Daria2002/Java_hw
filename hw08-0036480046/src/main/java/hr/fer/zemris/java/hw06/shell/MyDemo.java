package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MyDemo {

	public static void main(String[] args) {
		Path path = Paths.get("/home/daria/Desktop").toAbsolutePath().normalize();
		
		System.out.println(path.resolve(Paths.get("nekiNoviF").normalize()));
		System.out.println(Paths.get(path.toString()));
	}
	
}
