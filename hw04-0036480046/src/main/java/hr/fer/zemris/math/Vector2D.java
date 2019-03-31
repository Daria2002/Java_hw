package hr.fer.zemris.math;

public class Vector2D {

	private static final double TOLERANCE = 0.0001;
	
	private double x;
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void translate(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	public void rotate(double angle) {
		double helpX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double helpY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		this.x = helpX;
		this.y = helpY;
	}
	
	public Vector2D rotated(double angle) {
		double helpX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double helpY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		
		return new Vector2D(helpX, helpY);
	}
	
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
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
