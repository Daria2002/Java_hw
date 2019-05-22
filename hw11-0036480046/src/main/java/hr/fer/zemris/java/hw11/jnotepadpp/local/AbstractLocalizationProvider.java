package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents abstract localization provider, it implements ILocalizationProvider
 * @author Daria Matković
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/** List of ILocalizationListeners **/
	List<ILocalizationListener> listeners;
	
	/**
	 * Constructor for AbstractLocalizationProvider that initialize listeners
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * This method notifies all listeners when localization change
	 */
	void fire() {
		for(ILocalizationListener listener:listeners) {
			listener.localizationChanged();
		}
	}
}
