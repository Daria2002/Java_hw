package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

public class MyDrawingModel implements DrawingModel {
	
	List<GeometricalObject> objects = new ArrayList<GeometricalObject>();
	List<DrawingModelListener> listeners = new ArrayList<DrawingModelListener>();
	Boolean modificationFlag = false;
	
	
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	@Override
	public void remove(GeometricalObject object) {
		modificationFlag = true;
		objects.remove(object);
	}
	
	@Override
	public boolean isModified() {
		return modificationFlag;
	}
	
	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}
	
	@Override
	public void clearModifiedFlag() {
		modificationFlag = false;
	}
	
	@Override
	public void clear() {
		objects = new ArrayList<GeometricalObject>();
		modificationFlag = true;
	}
	
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}
	
	@Override
	public void add(GeometricalObject object) {
		modificationFlag = true;
		objects.add(object);
	}
	
}
