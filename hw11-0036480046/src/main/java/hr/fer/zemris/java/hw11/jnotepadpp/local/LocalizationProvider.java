package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is localization provider that extends AbstractLocalizationProvider.
 * This class is singleton.
 * @author Daria Matković
 *
 */
public final class LocalizationProvider extends AbstractLocalizationProvider {
	/** static final instance of localization provider **/
	private static final LocalizationProvider instance = new LocalizationProvider();
	/** language **/
	private static String language;
	/**  resource bundle **/
	private static ResourceBundle bundle;
	
	@Override
	public String getString(String string) {
		return bundle.getString(string);
	}
	
	/**
	 * This method sets language
	 * @param value new language 
	 */
	public void setLanguage(String value) {
		language = value;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));

		fire();
	}
	
	/**
	 * Localization provide constructor that initialize English as default language
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
	}
	
	/**
	 * This class returns instance of LocalizationProvider
	 * @return instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
}
