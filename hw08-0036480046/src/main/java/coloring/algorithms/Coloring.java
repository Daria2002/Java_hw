package coloring.algorithms;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

public class Coloring implements Consumer<Pixel>, Function<Pixel, Set<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	private static Pixel reference;
	private static Picture picture;
	private static int fillColor;
	private static int refColor;
	
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.picture = picture;
		this.fillColor = fillColor;
		this.reference = reference;
		
		refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean test(Pixel t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Pixel> apply(Pixel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(Pixel t) {
		// TODO Auto-generated method stub
		
	}
}
