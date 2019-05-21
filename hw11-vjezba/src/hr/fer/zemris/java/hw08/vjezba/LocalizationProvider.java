package hr.fer.zemris.java.hw08.vjezba;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocalizationProvider extends AbstractLocalizationProvider {

	private static final LocalizationProvider instance = new LocalizationProvider();
	private static String language;
	private static ResourceBundle bundle;
	
	@Override
	public String getString(String string) {
		return bundle.getString(string);
	}
	
	void setLanguage(String string) {
		language = string;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi",
				Locale.forLanguageTag(language));

		fire();
	}
	
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi",
				Locale.forLanguageTag(language));
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
}
