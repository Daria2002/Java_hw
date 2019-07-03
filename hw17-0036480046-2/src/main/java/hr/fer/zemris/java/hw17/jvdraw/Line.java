package hr.fer.zemris.java.hw17.jvdraw;

public class Line extends GeometricalObject {

	private static final String NAME = "LINE";
	int x0;
	int y0;
	int x1;
	int y1;
	int[] RGB = new int[3];
	
	public static String getName() {
		return NAME;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
