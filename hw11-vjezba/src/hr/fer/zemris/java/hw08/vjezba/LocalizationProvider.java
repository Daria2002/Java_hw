package hr.fer.zemris.java.hw08.vjezba;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocalizationProvider extends AbstractLoccalizationProvider {

	private static LocalizationProvider instance = new LocalizationProvider();
	private static String language;
	private static ResourceBundle bundle;
	
	@Override
	public String getString(String string) {
		return instance.bundle.getString(string);
	}
	
	void setLanguage(String string) {
		instance.language = string;
		Locale locale = Locale.forLanguageTag(instance.language);
		instance.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi",
				locale);

		fire();
	}
	
	private LocalizationProvider() {
		instance.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		instance.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi",
				locale);
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
}
