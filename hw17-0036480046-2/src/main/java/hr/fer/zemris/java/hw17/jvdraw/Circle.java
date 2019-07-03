package hr.fer.zemris.java.hw17.jvdraw;

public class Circle extends GeometricalObject {

	private static final String NAME = "CIRCLE";
	int centerX;
	int centerY;
	int radius;
	int[] RGB = new int[3];
	
	public static String getName() {
		return NAME;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
