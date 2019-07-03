package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;
import javax.swing.JList;

public class DrawingObjectListModel extends AbstractListModel<DrawingModel>
	implements DrawingModelListener {

	DrawingModel drawingModel;
	
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DrawingModel getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	
	
}
