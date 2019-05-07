package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class Raytracer {

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
				
				Point3D zAxis = new Point3D(1, 0, 0);
				Point3D yAxis = new Point3D(0, 1, 0);
				Point3D xAxis = new Point3D(0, 0, 1);
				// G
				Point3D viewPosition = new Point3D(0, 0, 0);
				Point3D screenCorner = viewPosition.sub(
						new Point3D(-horizontal/2, vertical/2, 0));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
					
						Point3D screenPoint = screenCorner.add(
								new Point3D(x * horizontal / (width-1),
										-y * vertical / (height-1), 0));
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						boolean intersectionExists = tracer(scene, ray, rgb);
						
						if(intersectionExists) {
							red[offset] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						} else {
							red[offset] = 0;
							green[offset] = 0;
							blue[offset] = 0;
						}
						
						short[] colors = determineColorFor(screenPoint, scene, ray);
						
						red[offset] = colors[0];
						green[offset] = colors[1];
						blue[offset] = colors[2];
						
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				
			}

			private boolean tracer(Scene scene, Ray ray, short[] rgb) {
				for(GraphicalObject obj : scene.getObjects()) {
					if(obj.findClosestRayIntersection(ray) != null) {
						return true;
					}
				}
				return false;
			}
			
			private short[] determineColorFor(Point3D s, Scene scene, Ray ray) {
				short[] color = new short[]{15, 15, 15};
				
				for (LightSource light : scene.getLights()) {
					
					Ray rHelp = new Ray(light.getPoint(), s);
					Point3D sHelp = scene.getObjects().get(0)
							.findClosestRayIntersection(rHelp).getPoint();
					
					if(sHelp != null && sHelp.sub(light.getPoint()).norm() 
							< sHelp.sub(light.getPoint()).norm()) {
						continue;
					}
					
					color[0] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKdr();
					color[0] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKrr();
					
					color[1] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKdb();
					color[1] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKrb();
					
					color[2] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKdg();
					color[2] += scene.getObjects().get(0)
							.findClosestRayIntersection(ray).getKrg();
				}
				
				return color;
			}
		};
	}
}
