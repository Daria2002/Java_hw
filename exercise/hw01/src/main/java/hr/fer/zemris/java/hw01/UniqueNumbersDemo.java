package hr.fer.zemris.java.hw01;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersDemo {

	public static void main(String[] args) {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		
		System.out.println("glava.value: " + glava.value);
		System.out.println("glava.left.value: " + glava.left.value);
		System.out.println("glava.left.right.value: " + glava.left.right.value);
		System.out.println("glava.right.value: " + glava.right.value);
		System.out.println("size: " + UniqueNumbers.treeSize(glava));
		System.out.println("contains 10: " + UniqueNumbers.containsValue(glava, 10));
		System.out.println("contains 76: " + UniqueNumbers.containsValue(glava, 76));
	}
	
}
