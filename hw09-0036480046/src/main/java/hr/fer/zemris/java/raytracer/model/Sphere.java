package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.math.Vector3;

/**
 * This class represents sphere that can exist in scene.
 * @author Daria Matković
 *
 */
public class Sphere extends GraphicalObject {

	/** center **/
	Point3D center;
	/** radius **/
	double radius;
	/** diffuse component for red color **/
	double kdr;
	/** diffuse component for green color **/
	double kdg;
	/** diffuse component for blue color **/
	double kdb;
	/** reflective component for red color **/
	double krr;
	/** reflective component for green color **/
	double krg;
	/** reflective component for blue color **/
	double krb;
	/** shininess factor **/
	double krn;
	/** tolerance for comparing double values **/
	private static final double TOL = 1E-5;
	
	/**
	 * Constructor initialize local variables
	 * @param center center
	 * @param radius radius
	 * @param kdr diffuse component for red color
	 * @param kdg diffuse component for green color
	 * @param kdb diffuse component for blue color
	 * @param krr reflective component for red color
	 * @param krg reflective component for green color
	 * @param krb reflective component for blue color
	 * @param krn shininess factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	/**
	 * Calculates intersection point
	 * @param ray ray that intersects sphere
	 * @return intersection point
	 */
	private IntersectionStruct getPointOnSurface(Ray ray) {
		Point3D oc = ray.start.sub(center);
		
	    double a = ray.direction.scalarProduct(ray.direction);
	    double b = 2.0 * oc.scalarProduct(ray.direction);
	    double c = oc.scalarProduct(oc) - radius*radius;
	    double discriminant = b*b - 4*a*c;
	    
	    // doesn't touch the sphere
	    if(discriminant < 0){
	        return null;
	    }
	    
	    double t0 = (-b - Math.sqrt(discriminant))/(2*a);
	    
	    // touch sphere in one point, discriminant = 0
	    if(discriminant == 0) {
	    	if(t0 < 0) {
	    		return null;
	    	}
	    	
	    	return new IntersectionStruct(
	    			ray.direction.scalarMultiply(t0).add(ray.start),
	    			t0, true);
	    }
	    
	    // two points 
    	double t1 = (-b + Math.sqrt(discriminant))/(2*a);
    	
    	if(t0 - t1 < TOL && t0 - 0 > TOL) {
    		return new IntersectionStruct(
	    			ray.direction.scalarMultiply(t0).add(ray.start),
	    			t0, true);
    		
    	} else if(t1 - t0 < TOL && t1 - 0 > TOL) {
    		return new IntersectionStruct(
	    			ray.direction.scalarMultiply(t1).add(ray.start),
	    			t1, true);
    		
    	} else if(t1 - 0 > TOL) {
    		return new IntersectionStruct(
	    			ray.direction.scalarMultiply(t1).add(ray.start),
	    			t1, false);
    		
    	} else if(t0 - 0 > TOL) {
    		return new IntersectionStruct(
	    			ray.direction.scalarMultiply(t0).add(ray.start),
	    			t0, false);
    	}
    	
    	return null;
	}
	
	/**
	 * This class represents structure that describes intersection point.
	 * @author Daria Matković
	 *
	 */
	private static class IntersectionStruct {
		/** intersection point **/
		private Point3D intersectionPoint;
		/** flag for ray direction **/
		private boolean outer;
		/** distance between ray source and intersection point**/
		private double distance;
		
		/**
		 * This method defines constructor for intersection structure
		 * @param intersectionPoint  intersection point
		 * @param distance distance
		 * @param outer ray direction
		 */
		public IntersectionStruct(Point3D intersectionPoint, 
				double distance, boolean outer) {
			this.intersectionPoint = intersectionPoint;
			this.outer = outer;
			this.distance = distance;
		}
	}
	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		IntersectionStruct intersection = getPointOnSurface(ray);
		
		if(intersection == null) {
			return null;
		}
		
		RayIntersection rayIntersection = new RayIntersection(
				intersection.intersectionPoint, intersection.distance,
				intersection.outer) {
			
			@Override
			public Point3D getNormal() {
				return Ray.fromPoints(center, intersection.intersectionPoint).direction;
			}

			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
		
		return rayIntersection;
	}
}
