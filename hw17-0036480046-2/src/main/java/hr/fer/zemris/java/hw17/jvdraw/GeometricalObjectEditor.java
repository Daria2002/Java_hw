package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.JPanel;

abstract class GeometricalObjectEditor extends JPanel {
	public abstract void checkEditing();
	public abstract void acceptEditing();
}