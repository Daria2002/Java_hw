package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * This class implements Consumer, Function, Predicate and Supplier.
 * @author Daria Matković
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>,
Predicate<Pixel>, Supplier<Pixel> {

	/** first Pixel **/
	private static Pixel reference;
	/** picture **/
	private static Picture picture;
	/** new color **/
	private static int fillColor;
	/** old color of first pixel **/
	private static int refColor;
	/** number of next positions **/
	private static final int numberOfNeighbours = 4;
	
	/**
	 * This method represents constructor that initialize first pixel, picture, 
	 * fill color and old color of first pixel
	 * @param reference first pixel
	 * @param picture picture
	 * @param fillColor fill color
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		Coloring.picture = picture;
		Coloring.fillColor = fillColor;
		Coloring.reference = reference;
		Coloring.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}
	
	@Override
	public boolean test(Pixel t) {
		if(picture.getPixelColor(t.x, t.y) == refColor) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> neigbors = new ArrayList<Pixel>();
		
		int[] newXValues = new int[numberOfNeighbours];
		newXValues[0] = t.x + 1;
		newXValues[1] = t.x - 1;
		newXValues[2] = t.x;
		newXValues[3] = t.x;
		
		int[] newYValues = new int[numberOfNeighbours];
		newYValues[0] = t.y;
		newYValues[1] = t.y;
		newYValues[2] = t.y + 1;
		newYValues[3] = t.y - 1;
		
		for(int i = 0; i < numberOfNeighbours; i++) {
			if(checkCoordinate(newXValues[i], newYValues[i])) {
				neigbors.add(new Pixel(newXValues[i], newYValues[i]));
			}
		}
		
		return neigbors;
	}

	private boolean checkCoordinate(int i, int j) {
		if(i >= 0 && j >= 0 && i <= picture.getWidth() - 1 && j <= picture.getHeight() - 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}
}
