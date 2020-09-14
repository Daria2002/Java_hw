package hr.fer.zemris.java.hw01.copy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
/**
 * Program adds entered values in binary tree, but only if value doesn't exists in tree.
 * Values greater than the current node are added as a right child, otherwise as a left child.
 * @author Daria Matković
 *
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueNumbers {
	
	//int result = 0;
	Set<String> set = new HashSet<String>();
	
	
	private static final int LIMIT_MIN = 0;
	private static final int LIMIT_MAX = 1048575;
	
	public int solution(String S) {
		Stack<Integer> stack = new Stack<Integer>();
		
		String[] elements = S.split("\\s+");
		String regex = "\\d+";
		
		
		
		for(int i = 0; i < elements.length; i++) {
			// element is number
			if(elements[i].matches(regex)) {
				stack.add(Integer.valueOf(elements[i]));
				continue;
			}
			
			if("DUP".equals(elements[i])) {
				if(stack.size() == 0) {
					return -1;
				}
				
				stack.add(stack.peek());
				continue;
			}
			
			if("POP".equals(elements[i])) {
				if(stack.size() == 0) {
					return -1;
				}
				
				stack.pop();
				continue;
			}
			
			if("+".equals(elements[i])) {
				if(stack.size() < 2) {
					return -1;
				}
				
				int element1 = stack.pop();
				int element2 = stack.pop();
				
				stack.push(element1 + element2);
				continue;
			}
			
			if("-".equals(elements[i])) {
				if(stack.size() < 2) {
					return -1;
				}
				
				int element1 = stack.pop();
				int element2 = stack.pop();
				
				stack.push(element1 - element2);
				continue;
			}
		}
		
		if(stack.size() == 0) {
			return -1;
		}
		
		return stack.pop();
		
	}

	public static void main(String[] args) {
	
	}
	
	public int clock(int A, int B, int C, int D) {
		String[] array = {String.valueOf(A), String.valueOf(B),
				String.valueOf(C), String.valueOf(D)};
		permutations(array, 0);
		return 0;
		//return result;
	}
	
	public void permutations(String[] array, int index) {
		if(index == array.length) {
			String formatToCheck = array[0] + array[1] + ":" + array[2] + array[3];
			if(validTime(formatToCheck) && !set.contains(formatToCheck)) {
				set.add(formatToCheck);
				
				//result++;
			}
			
		} else {
			for(int i = index; i < array.length; i++) {
				swap(array, index, i);
				
				permutations(array, index + 1);
				
				swap(array, index, i);
			}
		}
		
		
	}
	
	public void swap(String[] array, int index1, int index2) {
		String temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	public boolean validTime(String formatToCheck) {
		String format = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		Matcher matcher = Pattern.compile(format).matcher(formatToCheck);
		return matcher.matches();
	}
	
	public int solu(int N) {
		int[] digits = new int[10];
		
		while(N > 0) {
			digits[N % 10]++;
			N /= 10;
		}
		
		int result = 0;
		int helper = 1;
		
		for(int i = 0; i < 10; i++) {
			for(int j = digits[i]; j > 0; j--) {
				result += helper*i;
				
				if(result > 1e8) {
					return -1;
				}
				
				helper *= 10;
			}
		}
		
		return result;
	}

	public int sol1(int[] A) {
		Arrays.sort(A);
		
		int smallest = 1;
		
		for(int i = 0; i < A.length; i++) {
			if(A[i] <= 0) {
				continue;
			}
			
			if(A[i] > smallest) {
				return smallest;
			} else {
				smallest++;
			}
			
			if(A[i] == A[i+1]) {
				i++;
			}
		}
		return smallest;
		
	}
	
	public int sol(int[] A) {
		HashSet<Integer> set = new HashSet<Integer>();
		
		for(int i = 0; i < A.length; i++) {
			set.add(A[i]);
		}
		
		int smallest = 1;
		
		while(set.contains(smallest)) {
			smallest++;
		}
		
		return smallest;
		
	}
}
