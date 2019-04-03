package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Demonstration class
 * @author Daria Matković
 *
 */
public class Primjer { 
	
	/**
	 * Executes when program is run.
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		/*
		create collection and prints:
		Ante => 2
		Ivana => 5
		Jasna => 2
		Kristina => 5
		 */
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		// create collection:
		examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		// cartesian product
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n",
				pair1.getKey(), pair1.getValue(),
				pair2.getKey(), pair2.getValue());
			}
		}

		// removes Ivana
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2); 
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks.iterator();
		while(iter1.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter1.next();
			if(pair.getKey().equals("Ivana")) {
				iter1.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		} 
		System.out.println(examMarks.containsKey("Ivana"));

		// print all pairs and leaves collection empty
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
		
		// throws ConcurrentModificationException
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter4 = examMarks.iterator();
		while(iter4.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter4.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
		
		// throws IllegalStateException
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
		while(iter2.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
			if(pair.getKey().equals("Ivana")) {
				iter2.remove();
				iter2.remove();
			}
		}
	}
}
