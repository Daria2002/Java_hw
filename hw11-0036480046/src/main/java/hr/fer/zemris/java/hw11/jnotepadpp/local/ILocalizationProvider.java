package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class represents ILocalization provider
 * @author Daria Matković
 *
 */
public interface ILocalizationProvider {

	/**
	 * This method adds listener
	 * @param listener listener to add
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * This method removes listener
	 * @param listener listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * This method gets translation for given string
	 * @param string value to translate
	 * @return translation of given value
	 */
	String getString(String string);	
}
