package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

abstract class GeometricalObject {
	
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
// ...
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	public abstract void accept(GeometricalObjectVisitor v);
	
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	private void callListeners() {
		for(GeometricalObjectListener l:listeners) {
			l.geometricalObjectChanged(this);
		}
	}
// ...
}