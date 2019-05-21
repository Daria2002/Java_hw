package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.List;

public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationProvider {

	boolean connected;
	ILocalizationProvider provider;
	String language;
	ILocalizationListener listener;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	void disconnect() {
		provider.removeLocalizationListener(listener);
		connected = false;
	}
	
	void connect() {
		if(connected) {
			return;
		}
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
		connected = true;
		provider.addLocalizationListener(listener);
	}
	
	@Override
	public String getString(String string) {
		return provider.getString(string);
	}
}
