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
		return Arrays.copyOf(configuration, configuration.length);
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
}
