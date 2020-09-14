package hr.fer.zemris.java.hw08.vjezba;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider provider, 
			JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}
}
