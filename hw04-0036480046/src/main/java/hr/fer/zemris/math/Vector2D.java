package hr.fer.zemris.math;

/**
 * Class that represents 2D vector.
 * @author Daria Matković
 *
 */
public class Vector2D {
	/** tolerance for comparing double numbers **/
	private static final double TOLERANCE = 0.0001;
	/** X component **/
	private double x;
	/** Y component **/
	private double y;
	
	/**
	 * Constructor for vector that initialize x and y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	} 
	
	/**
	 * Returns x
	 * @return x
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Returns y
	 * @return y
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Translate vector for given offset vecor
	 * @param offset offset vecor
	 */
	public void translate(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Translates and returns new translated vector
	 * @param offset offset vector
	 * @return new translated vecor
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotates vector for given angle
	 * @param angle given angle of rotation
	 */
	public void rotate(double angle) {
		double helpX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double helpY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		this.x = helpX;
		this.y = helpY;
	}
	
	/**
	 * Rotates vector for given angle and returns new vector
	 * @param angle angle of rotation
	 * @return new rotated vector
	 */
	public Vector2D rotated(double angle) {
		double helpX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double helpY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		
		return new Vector2D(helpX, helpY);
	}
	
	/**
	 * Scales vector by given factor
	 * @param scaler scale factor
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Scales vector by given factor and returns new vector
	 * @param scaler scale factor
	 * @return scaled vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Copy vector to a new vector
	 * @return a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(x-other.x) > TOLERANCE)
			return false;
		if (Math.abs(y-other.y) > TOLERANCE)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
}
