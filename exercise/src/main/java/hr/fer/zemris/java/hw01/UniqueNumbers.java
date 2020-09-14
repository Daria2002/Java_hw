package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program adds entered values in binary tree, but only if value doesn't exists in tree.
 * Values greater than the current node are added as a right child, otherwise as a left child.
 * @author Daria Matković
 *
 */
public class UniqueNumbers {
	/**
	 * This class represents a single node of a binary child.
	 * @author Daria Matković
	 *
	 */
	public static class TreeNode {
		/*
		 * Left child of the binary tree node
		 */
		TreeNode left;
		/*
		 * Right child of the binary tree node
		 */
		TreeNode right;
		/*
		 * Value of the binary tree node
		 */
	    int value;
	}
	
	/**
	 * This method executes when program starts.
	 * @param args Program executes without arguments
	 */
	public static void main(String[] args) {
		TreeNode node = null;
		Scanner sc = new Scanner(System.in);
		
		String input = new String();
		while(!"kraj".equals(input)) {
			System.out.print("Unesite broj > ");
			input = sc.next();
			try {
				int intInput = Integer.parseInt(input);
				if(containsValue(node, intInput)) {
					System.out.println("Broj vec postoji. Preskačem.");
				}
				else {
					node = addNode(node, intInput);
					System.out.println("Dodano.");
				}
			} catch (NumberFormatException e) {
				if(!"kraj".equals(input)) {
					System.out.println("'" + input + "'" + " nije cijeli broj.");
				}
			}
		}
		
		System.out.print("Ispis od najmanjeg: ");
		printMinToMax(node);
		System.out.print("\nIspis od najvećeg: ");
		printMaxToMin(node);
		
		sc.close();
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
	
	/**
	 * Adds node to tree.
	 * @param node root node in tree
	 * @param value to add in tree
	 * @return
	 */
	public static TreeNode addNode(TreeNode node, int value) {
		if(node == null) {
			node = new TreeNode();
			node.value = value;
			node.left = null;
			node.right = null;
		}
		else if(value > node.value) {
			node.right = addNode(node.right, value);
		}
		else if(value < node.value) {
			node.left = addNode(node.left, value);
		}
		return node;
	}
	
	/**
	 * Calculates tree size.
	 * @param node root node
	 * @return tree size
	 */
	public static int treeSize(TreeNode node) {
		if(node == null) {
			return 0;
		}
		int size = treeSize(node.left) + treeSize(node.right) + 1;
		return size;
	}
	
	/**
	 * Checks if value exists in tree.
	 * @param node root node in tree
	 * @param value to search in tree
	 * @return true if value exists in tree, otherwise false
	 */
	public static Boolean containsValue(TreeNode node, int value) {
		if(node == null) {
			return false;
		}
		while(true && node != null) {
			if(node.value == value) {
				return true;
			}
			else if(node.value < value) {
				node = node.right;
			} 
			else {
				node = node.left;
			}
		}
		
		return false;
	}
	
}
