package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

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
			
			@Override
			public Point3D getNormal() {
				Point3D pointOnSurface = new Point3D();
				
				return pointOnSurface.sub(center);
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
