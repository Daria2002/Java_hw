package searching.demo;

import java.util.ArrayList;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Demo for bfsv algorithm.
 * @author Daria Matković
 *
 */
public class SlagalicaMain {

	/**
	 * This method is executed when program is run
	 * @param args takes string that represents numbers in puzzle
	 */
	public static void main(String[] args) {
		int[] elementsArray = charArrayToIntArray(args[args.length - 1].toCharArray());
		
		boolean check = checkElements(elementsArray);
		
		if(elementsArray == null || !check) {
			System.out.println("Input not ok");
			System.exit(0);
		}
		
		Slagalica slagalica = new Slagalica(
				new KonfiguracijaSlagalice(elementsArray));
		
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		
		if(rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
			
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			SlagalicaViewer.display(rješenje);
		}
	}
	
	/**
	 * Checks that numbers in range [0, 8] are in elementsArray
	 * @param elementsArray array to check
	 * @return true if numbers in range [0, 8] are in elementsArray, otherwise false
	 */
	private static boolean checkElements(int[] elementsArray) {
		List<Integer> elementsList = new ArrayList<Integer>();
		for(int el : elementsArray) {
			elementsList.add(el);
		}
		
		for(int i = 0; i < 9; i++) {
			if(!elementsList.contains(i)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Converts array of chars to array of ints
	 * @param charArray array of chars
	 * @return array of ints
	 */
	private static int[] charArrayToIntArray(char[] charArray) {
		int[] intArray = new int[9];
				
		int i = 0;
		for(char el : charArray) {
			intArray[i++] = Character.getNumericValue(el);
		}
		
		return intArray;
	}
}
