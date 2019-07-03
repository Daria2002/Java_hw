package hr.fer.zemris.java.hw17.jvdraw;

abstract class GeometricalObject {
// ...
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
	
	}
	
	public abstract void accept(GeometricalObjectVisitor v);
	
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		
	}
// ...
}