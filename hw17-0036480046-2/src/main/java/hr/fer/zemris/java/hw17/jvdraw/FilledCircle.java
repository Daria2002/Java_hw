package hr.fer.zemris.java.hw17.jvdraw;

public class FilledCircle extends GeometricalObject {

	private static final String NAME = "FCIRCLE";
	int centerX;
	int centerY;
	int radius;
	int[] outlineRGB = new int[3];
	int[] fillRGB = new int[3];
	
	public static String getName() {
		return NAME;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
