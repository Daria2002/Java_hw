package coloring.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		
		neigbors.add(new Pixel(t.x + 1, t.y));
		neigbors.add(new Pixel(t.x - 1, t.y));
		neigbors.add(new Pixel(t.x, t.y + 1));
		neigbors.add(new Pixel(t.x, t.y - 1));
		
		return neigbors;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(fillColor, t.x, t.y);
	}
}
