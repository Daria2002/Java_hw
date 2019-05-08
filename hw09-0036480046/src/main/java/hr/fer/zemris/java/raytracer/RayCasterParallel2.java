package hr.fer.zemris.java.raytracer;

import java.util.List;
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
 * RecursiveAction
 * @author Daria Matković
 *
 */
public class RayCasterParallel2 {
	/*
	public static void main(String[] args) {
		RayTracerViewer.show(
		getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}
	
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
		
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
		
		@Override
		public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width,
			int height, long requestNo, IRayTracerResultObserver observer,
			AtomicBoolean cancel) {
			
			}
		};
	}*/
}
