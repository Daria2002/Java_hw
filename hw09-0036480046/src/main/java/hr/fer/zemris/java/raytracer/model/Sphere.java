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
	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		RayIntersection rayIntersection = new RayIntersection(center, kdb, false) {
			
			/**
			 * This method calculates intersection point between ray and sphere
			 * @return intersection point dot(ray.direction, ray.direction);
			 */
			private Point3D getPointOnSurface() {
				Point3D oc = ray.direction.sub(center);
				
			    double x = ray.direction.scalarProduct(ray.direction);
			    double y = 2.0 * oc.scalarProduct(ray.direction);
			    double z = oc.scalarProduct(oc) - radius*radius;
			    double discriminant = y*y - 4*x*z;
			    
			    // two points
			    if(discriminant > 0) {
			    	return new Point3D(x, y, z);
			    }
			    
			    // doesn't touch the sphere
			    if(discriminant < 0){
			        return null;
			    }
			    
			    // touch sphere in one point
			    else{
			        return new Point3D(x, y, z);
			    }
			}
			
			@Override
			public Point3D getNormal() {
				Point3D pointOnSurface = getPointOnSurface();
				
				return Ray.fromPoints(center, pointOnSurface).direction;
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
		
		return null;
	}
}
