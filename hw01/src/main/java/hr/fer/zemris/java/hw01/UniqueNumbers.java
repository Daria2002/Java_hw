package hr.fer.zemris.java.hw01;

import java.util.Scanner;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbers {

	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String input;
		Integer value;
		TreeNode glava = null;
		
		while (true) {
			System.out.print("Unesite broj > ");
			input = sc.next();
			
			if("kraj".equals(input)) {
				break;
			}
			
			try {
				value = Integer.valueOf(input);
				
				if(containsValue(glava, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, value);
					System.out.println("Dodano.");
				}
				
			} catch (Exception e) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
			
		}
		System.out.print("Ispis od najmanjeg: ");
		printMinToMax(glava);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		printMaxToMin(glava);
	}
	

	/**
	 * Prints tree values from minimum to maximum.
	 * @param node is root node
	 */
	public static void printMinToMax(TreeNode node) {
		if(node == null) {
			return;
		}
		printMinToMax(node.left);
		System.out.print(node.value + " ");
		printMinToMax(node.right);
	}
	
	/**
	 * Prints tree values from maximum to minimum.
	 * @param node is root node 
	 */
	public static void printMaxToMin(TreeNode node) {
		if(node == null) {
			return;
		}
		printMaxToMin(node.right);
		System.out.print(node.value + " ");
		printMaxToMin(node.left);
	}
	
	public static TreeNode addNode(TreeNode glava, int value) {
		if(glava == null) {
			glava = new TreeNode();
			glava.value = value;
			
		} else if(glava.value > value) {
			glava.left = addNode(glava.left, value);
			
		} else if(glava.value < value) {
			glava.right = addNode(glava.right, value);
		}
		
		return glava;
	}
	
	public static int treeSize(TreeNode glava) {
		if(glava == null) {
			return 0;
		}
		
		return treeSize(glava.left) + treeSize(glava.right) + 1;
	}
	
	public static boolean containsValue(TreeNode glava, int value) {
		while (true) {
			if(glava == null) {
				return false;
			} else if(glava.value > value) {
				glava = glava.left;
			} else if(glava.value < value) {
				glava = glava.right;
			} else {
				return true;
			}
		}
	}
}
