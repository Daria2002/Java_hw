package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class represents localization provider bridge that extends 
 * AbstractLocalizationProvider and implements ILocalizationProvider
 * @author Daria Matković
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationProvider {
	/** flag for connection status **/
	boolean connected;
	/** provider **/
	ILocalizationProvider provider;
	/** language **/
	String language;
	/** listener **/
	ILocalizationListener listener;
	
	/** LocalizationProviderBridge constructor **/
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * Disconnects listener
	 */
	void disconnect() {
		provider.removeLocalizationListener(listener);
		connected = false;
	}
	
	/**
	 * Connect listener
	 */
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
