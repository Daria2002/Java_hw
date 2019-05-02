package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

/**
 * Demo program for bfs algoritham for solvving puzzle.
 * @author Daria Matković
 *
 */
public class SlagalicaDemo {
	
	/**
	 * This method is executed when program is run
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		Slagalica slagalica = new Slagalica(
				new KonfiguracijaSlagalice(new int[] {2,3,0,1,4,6,7,5,8})
	);
		
	Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfs(slagalica, slagalica, slagalica);
	
	if(rješenje == null) {
		System.out.println("Nisam uspio pronaći rješenje.");
		
	} else {
		System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
		List<KonfiguracijaSlagalice> lista = new ArrayList<>();
		Node<KonfiguracijaSlagalice> trenutni = rješenje;
		
		while(trenutni != null) {
			lista.add(trenutni.getState());
			trenutni = trenutni.getParent();
		}
		
		Collections.reverse(lista);
		lista.stream().forEach(k -> {
			System.out.println(k);
			System.out.println();});
		}
	}
}
