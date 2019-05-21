package hr.fer.zemris.java.hw08.vjezba;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {

	public FormLocalizationProvider(ILocalizationProvider provider, 
			JFrame frame) {
		super(provider);
		frame.addWindowListener(this);
	}

	public class SomeFrame extends JFrame {
		private FormLocalizationProvider flp;
		public SomeFrame() {
		super();
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		}
		}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
