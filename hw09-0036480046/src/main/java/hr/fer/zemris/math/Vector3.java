package hr.fer.zemris.math;

/**
 * This class represents unchangeable 3 component vector.
 * @author Daria Matković
 *
 */
public class Vector3 {

	/** x component **/
	private double x;
	/** y component **/
	private double y;
	/** z component **/
	private double z;
	
	/**
	 * This method represents constructor that initialize x, y and z component
	 * @param x initial value of x
	 * @param y initial value of y
	 * @param z initial value of z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * This method calculate norm of vector.
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	/**
	 * This method calculate normalized vector
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x/norm(), y/norm(), z/norm());
	}
	
	/**
	 * This method adds other vector to vector
	 * @param other vector to add 
	 * @return result vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Subtract other vector from vector
	 * @param other vector to subtract
	 * @return result vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * This method calculate scalar product of other and vector
	 * @param other vector for scalar product
	 * @return scalar product of other and vector
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	/**
	 * This method returns cross product of vector and other
	 * @param other vector for cross product
	 * @return result of cross product
	 */
	public Vector3 cross(Vector3 other) {
		double newX = y * other.z - z * other.y;
	    double newY = x * other.z - z * other.x; 
	    double newZ = x * other.y - y * other.x;
	    
		return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * This method scales vector with given factor
	 * @param s factor for scaling
	 * @return result of scaling
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}
	
	/**
	 * This method returns cosines of angle between vector and other
	 * @param other vector from which angle is calculated
	 * @return cosinus of angle
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (other.norm() * this.norm());
	}
	
	/**
	 * Returns x
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns y
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns z
	 * @return z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Converts to array
	 * @return array of three elements, where each element represents each component
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
}
