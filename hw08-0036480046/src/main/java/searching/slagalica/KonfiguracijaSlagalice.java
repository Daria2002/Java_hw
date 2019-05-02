package searching.slagalica;

import java.util.Arrays;

/**
 * This class represents configuration of field.
 * @author Daria Matković
 *
 */
public class KonfiguracijaSlagalice {
	/** configuration **/
	private int[] configuration = new int[9];
	/** 0 represents zero **/
	private static final int SPACE = 0;
	
	/**
	 * This constructor initialize configuration
	 * @param configuration
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		this.configuration = configuration;
	}

	/**
	 * Gets configuration of field
	 * @return configuration
	 */
	public int[] getPolje() {
		int[] x = Arrays.copyOf(configuration, configuration.length);
		return x;
	}
	
	/**
	 * This method returns index of space. 0 represents space
	 * @return index of space
	 */
	public int indexOfSpace() {
		//return Arrays.asList(configuration).indexOf(SPACE);
		for(int i = 0; i < configuration.length; i++) {
			if(configuration[i] == SPACE) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < configuration.length; i++) {
			// if space, print *
			if(configuration[i] == 0) {
				result.append("* ");
				
			} else {
				result.append(configuration[i] + " ");
			}
			
			if(i % 3 == 2 && i != configuration.length - 1) {
				result.append("\n");
			}
		}
		
		return result.toString();
	}
}
