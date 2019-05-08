package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents parallelized calculation using Fork-Join framework and
 * RecursiveAction. RecursiveAction is ForkJoinTask's subclass for tasks that
 * don't return values.
 * @author Daria Matković
 *
 */
public class RayCasterParallel extends RecursiveAction {
	/**
	 * This method is executed when program is run.
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10,0,0),
				new Point3D(0,0,0), new Point3D(0,0,10), 20, 20);
	}
	
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer,
					AtomicBoolean atomicBoolean) {
				
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
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				int offset = 0;
				
				// iterate through all pixels in screen
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
					
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(x * horizontal/(width-1)))
								.sub(yAxis.scalarMultiply(y*vertical/(height-1)));
	
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						short[] rgb = new short[3];
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				
			}

			/**
			 * 
			 * @param scene scene
			 * @param ray ray 
			 * @param rgb color
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {
				
				RayIntersection closestEl = getClosestRayIntersection(scene.getObjects(), ray);
				
				if(closestEl == null) {
					rgb = null;
					return;
				}
				
				rgb = determineColorFor(closestEl, scene, ray, rgb);
			}
			/*	
				short[] rgb = new short[] {0, 0, 0};
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;
				RayIntersection closest = getClosestRayIntersection(scene.getObjects(), ray);
				
				if(closest==null) {
					return null;
				}
				
				rgb[0] = 255;
				rgb[1] = 255;
				rgb[2] = 255;
				return rgb;
			}
			*/
			private RayIntersection getClosestRayIntersection(List<GraphicalObject> objects, 
					Ray ray) {

				RayIntersection closestEl = null;

				// searching for closest element
				for(GraphicalObject obj : objects) {
					
					RayIntersection testClosest = obj.findClosestRayIntersection(ray);
					
					if(testClosest != null) {
						if(closestEl == null || testClosest.getDistance() > testClosest.getDistance()) {
							closestEl = testClosest;
						}
					}
				}
				
				return closestEl;
			}
			
			class PhongParams {
				
				Point3D l;
				Point3D n;
				Point3D r;
				Point3D v;
				
				public PhongParams(Point3D l, Point3D n, Point3D r, Point3D v) {
					this.l = l;
					this.n = n;
					this.r = r;
					this.v = v;
				}
			}
			
			private PhongParams calculatePhongParams(RayIntersection s, 
					LightSource light, Ray ray) {
				
				Point3D l = ray.fromPoints(s.getPoint(), light.getPoint()).direction;
				Point3D n = s.getNormal();
				Point3D d = l.negate();
				Point3D r = d.sub(n.scalarMultiply(d.scalarProduct(n) * 2));
				Point3D v = ray.fromPoints(s.getPoint(), ray.start).direction;
				
				return new PhongParams(l, n, r, v);
			}
			
			private short[] determineColorFor(RayIntersection s, Scene scene, Ray ray, short[] color) {
				/*short[] */color[0] = 15;
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
					
					if(reflectionComponent < 0) {
						reflectionComponent = 0;
					}
					
					reflectionComponent = Math.pow(reflectionComponent, s.getKrn());
					
					color[0] += (reflectionComponent * s.getKrr() + diffuseComponent * s.getKdr()) * light.getR();
					color[1] += (reflectionComponent * s.getKrg() + diffuseComponent * s.getKdg()) * light.getG();
					color[2] += (reflectionComponent * s.getKrb() + diffuseComponent * s.getKdb()) * light.getB();
					
				}
				
				return color;
			}
		};
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		
	}
}
