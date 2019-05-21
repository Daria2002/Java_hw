package hr.fer.zemris.java.hw08.vjezba;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLoccalizationProvider implements ILocalizationProvider {

	List<ILocalizationListener> listeners;
	
	public AbstractLoccalizationProvider() {
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
	
	void fire() {
		for(ILocalizationListener listener:listeners) {
			listener.localizationChanged();
		}
	}
	
}
