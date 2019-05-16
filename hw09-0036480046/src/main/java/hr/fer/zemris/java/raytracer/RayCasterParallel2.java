package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;


/**
 * This class represents demonstration program for raytracing objects during
 * animation where objects are rotated.
 * @author Daria Matković
 *
 */
public class RayCasterParallel2 {
	
	/**
	 * This method is executed when program run
	 * @param args takes no arguments.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
		getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
		);
	}
	
	/**
	 * This method represents ray tracer animator
	 * @return ray tracer animator
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;
			
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}
			
			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0,0,10);
			}
			
			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2,0,-0.5);
			}
			
			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}
			
			@Override
			public Point3D getEye() { // changes in time
				double t = (double)time / 10000 * 2 * Math.PI;
				double t2 = (double)time / 5000 * 2 * Math.PI;
				double x = 50*Math.cos(t);
				double y = 50*Math.sin(t);
				double z = 30*Math.sin(t2);
				return new Point3D(x,y,z);
			}
		};
	}
		
	/**
	 * This method represents ray tracer producer.
	 * @return ray tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width,
			int height, long requestNo,
			IRayTracerResultObserver observer,
			AtomicBoolean cancel) {
			
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(
						zAxis.scalarProduct(viewUp.normalize()))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2))
						.add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene2();
				
				int offset = 0;
				
				ForkJoinPool pool = new ForkJoinPool();
				
				Task task = new Task(screenCorner, xAxis, yAxis, horizontal,
						vertical, height, width, 0, scene, red, green, blue, eye,
						offset, view, height);
				
				pool.invoke(task);
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				
			}
			
			/**
			 * This class represents task that implements RecursiveAction. 
			 * The task will call itself recursively until a given threshold is 
			 * reached 
			 * @author Daria Matković
			 *
			 */
			class Task extends RecursiveAction {
				/** screen corner **/
				Point3D screenCorner;
				/** x axis **/
				Point3D xAxis;
				/** y axis **/
				Point3D yAxis;
				/** horizontal size **/
				double horizontal;
				/** vertical size **/
				double vertical;
				/** y max **/
				int yMax;
				/** x max **/
				int xMax;
				/** y min **/
				int yMin;
				/** scene **/
				Scene scene;
				/** rgb array **/
				short[] rgb;
				/** red array **/
				short[] red;
				/** green array **/
				short[] green;
				/** blue array **/
				short[] blue;
				/** observer point **/
				Point3D eye;
				/** offset **/
				int offset;
				/** view point **/
				Point3D view;
				/** height **/
				int height;
				/** threshold for creating new objects **/
				static final int THRESHOLD = 3;
				/** tolerance for comparing double values **/
				private static final double TOL = 1E-5;
				
				/**
				 * Constructor that initializes local variables 
				 * @param screenCorner screen corner
				 * @param xAxis x axis
				 * @param yAxis y axis
				 * @param horizontal horizontal size
				 * @param vertical vertical size
				 * @param yMax maximum value of y 
				 * @param xMax maximum value of x
				 * @param yMin minimum value of y
				 * @param scene scene
				 * @param red red array
				 * @param green green array
				 * @param blue blue array
				 * @param eye observer point
				 * @param offset offset for colors array
				 * @param view view point
				 * @param height height
				 */
				public Task(Point3D screenCorner, Point3D xAxis, Point3D yAxis,
						double horizontal, double vertical, int yMax, int xMax, int yMin,
						Scene scene, short[] red, short[] green, short[] blue, Point3D eye,
						int offset, Point3D view, int height) {
					super();
					this.screenCorner = screenCorner;
					this.xAxis = xAxis;
					this.yAxis = yAxis;
					this.horizontal = horizontal;
					this.vertical = vertical;
					this.yMax = yMax;
					this.xMax = xMax;
					this.yMin = yMin;
					this.scene = scene;
					this.red = red;
					this.green = green;
					this.blue = blue;
					this.eye = eye;
					this.offset = offset;
					this.view = view;
					this.height = height;
				}
				
				/**
				 * This class represents tracer for given ray in given scene
				 * @param scene scene
				 * @param ray ray
				 * @param rgb rgb array
				 * @return array of short that represents new rgb array
				 */
				private short[] tracer(Scene scene, Ray ray, short[] rgb) {
					
					RayIntersection closestEl = getClosestRayIntersection(scene.getObjects(), ray);
					
					if(closestEl == null) {
						rgb = null;
						return rgb;
					}
					
					rgb = determineColorFor(closestEl, scene, ray, rgb);
					
					return rgb;
				}

				/**
				 * Determine color for given ray intersection, in given scene, for given ray
				 * @param s ray intersection 
				 * @param scene scene
				 * @param ray ray
				 * @param color color array
				 * @return new color array
				 */
				private short[] determineColorFor(RayIntersection s, Scene scene, Ray ray, short[] color) {
					color[0] = 15;
					color[1] = 15;
					color[2] = 15;
					
					for (LightSource light : scene.getLights()) {
						
						Ray rHelp = Ray.fromPoints(light.getPoint(), s.getPoint());
						
						RayIntersection sHelp = getClosestRayIntersection(
								scene.getObjects(), rHelp);
						
						if(sHelp != null && Math.abs(sHelp.getPoint().sub(light.getPoint()).norm() 
								- s.getPoint().sub(light.getPoint()).norm()) > 0.01) {
							continue;
						}
						
						PhongParams phongParams = calculatePhongParams(s, light, ray);
						
						// diffuse component to illumination
						double diffuseComponent = Math.max(0, phongParams.n.scalarProduct(phongParams.l));
						double reflectionComponent = phongParams.r.scalarProduct(phongParams.v);
						
						if(reflectionComponent - 0 < TOL) {
							reflectionComponent = 0;
						}
						
						reflectionComponent = Math.pow(reflectionComponent, s.getKrn());
						
						color[0] += (reflectionComponent * s.getKrr() + diffuseComponent * s.getKdr()) * light.getR();
						color[1] += (reflectionComponent * s.getKrg() + diffuseComponent * s.getKdg()) * light.getG();
						color[2] += (reflectionComponent * s.getKrb() + diffuseComponent * s.getKdb()) * light.getB();
						
					}
					
					return color;
				}
				
				/**
				 * Gets closest ray intersection
				 * @param objects list of objects
				 * @param ray ray 
				 * @return closest ray intersection
				 */
				private RayIntersection getClosestRayIntersection(List<GraphicalObject> objects, 
						Ray ray) {

					RayIntersection closestEl = null;

					// searching for closest element
					for(GraphicalObject obj : objects) {
						
						RayIntersection testClosest = obj.findClosestRayIntersection(ray);
						
						if(testClosest != null) {
							if(closestEl == null || closestEl.getDistance() > testClosest.getDistance()) {
								closestEl = testClosest;
							}
						}
					}
					
					return closestEl;
				}
				
				/**
				 * This class represents phong parameters
				 * @author Daria Matković
				 *
				 */
				class PhongParams {
					/** direction vector from the point on the surface toward each light source **/
					Point3D l;
					/** normal at this point on the surface **/
					Point3D n;
					/**  direction that a perfectly reflected ray of light would take from this point on the surface **/
					Point3D r;
					/** direction pointing towards the viewer **/
					Point3D v;
					
					/**
					 * Constructor that initialize phong params
					 * @param l direction vector from the point on the surface toward each light source
					 * @param n normal at this point on the surface
					 * @param r direction that a perfectly reflected ray of light would take from this point on the surface
					 * @param v direction pointing towards the viewer
					 */
					public PhongParams(Point3D l, Point3D n, Point3D r, Point3D v) {
						this.l = l;
						this.n = n;
						this.r = r;
						this.v = v;
					}
				}
				
				/**
				 * This method calculates phong params
				 * @param s given ray intersection
				 * @param light light source
				 * @param ray ray
				 * @return phong params
				 */
				private PhongParams calculatePhongParams(RayIntersection s, 
						LightSource light, Ray ray) {
					
					Point3D l = Ray.fromPoints(s.getPoint(), light.getPoint()).direction;
					Point3D n = s.getNormal();
					Point3D d = l.negate();
					Point3D r = d.sub(n.scalarMultiply(d.scalarProduct(n) * 2));
					Point3D v = Ray.fromPoints(s.getPoint(), ray.start).direction;
					
					return new PhongParams(l, n, r, v);
				}
				
				/**
				 * This method runs task.
				 * @param screenCorner screen corner
				 * @param xAxis x axis
				 * @param yAxis y axis
				 * @param horizontal horizontal size
				 * @param vertical vertical size
				 * @param yMax y max
				 * @param xMax x max
				 * @param yMin y min
				 * @param scene scene
				 * @param red red array
				 * @param green green array
				 * @param blue blue array
				 * @param eye observer point
				 * @param offset offset in color array
				 * @param view view point
				 * @param height height
				 */
				private void doTask(Point3D screenCorner, Point3D xAxis, Point3D yAxis,
						double horizontal, double vertical, int yMax, int xMax, int yMin,
						Scene scene, short[] red, short[] green, short[] blue,
						Point3D eye, int offset, Point3D view, int height) {
					
					// iterate through all pixels in screen
					for(int y = yMin; y < yMax; y++) {
						offset = y * xMax;
						for(int x = 0; x < xMax; x++) {
							
							Point3D screenPoint = screenCorner.add(
									xAxis.scalarMultiply(x * horizontal/(xMax-1)))
									.sub(yAxis.scalarMultiply(y * vertical/(height-1)));

							Ray ray = Ray.fromPoints(eye, screenPoint);
							
							rgb = new short[] {0, 0, 0};
							
							rgb = tracer(scene, ray, rgb);
							
							if(rgb == null) {
								rgb = new short[] {0, 0, 0};
							}
							
							red[offset] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
							
							offset++;
						}
					}
				}

				@Override
				protected void compute() {

					if(yMax-yMin < THRESHOLD) {
						doTask(screenCorner, xAxis, yAxis, horizontal,
								vertical, yMax, xMax, yMin, scene, red, green,
								blue, eye, offset, view, height);
						
					} else {
						Task first = new Task(screenCorner, xAxis, yAxis, horizontal, vertical,
								(yMax-yMin)/2 + yMin, xMax, yMin, scene, red, green, blue, eye,
								offset, view, height);
						
						Task second = new Task(screenCorner, xAxis, yAxis, horizontal, vertical,
								yMax, xMax, (yMax-yMin)/2 + yMin, scene, red, green, blue, eye,
								offset, view, height);
						
						invokeAll(first, second);
						
					}	
				}	
			}
		};
	}
}
