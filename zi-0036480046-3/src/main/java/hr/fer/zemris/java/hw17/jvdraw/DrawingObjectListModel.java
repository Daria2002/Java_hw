package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;
import javax.swing.JList;

/**
 * This class represents drawing object list model
 * @author Daria Matković
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
	implements DrawingModelListener {

	/** drawing model */
	DrawingModel drawingModel;
	
	/**
	 * Constructor for drawing object list model
	 * @param drawingModel
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		super.fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		super.fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}
}
