package hr.fer.zemris.java.fractals;

import java.awt.Dimension;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
	
		Scanner getRoot = new Scanner(System.in);
		
		int i = 1;
		List<Complex> complexNumbers = new ArrayList<>();
		
		while (true) {
			System.out.printf("Root %d>", i);
			
			String root = getRoot.nextLine();
			root = root.strip().replaceAll("\\s+", "");
			
			if("done".equalsIgnoreCase(root)) {
				if(i < 3) {
					System.out.println("Enter at least 2 roots.");
					i = 0;
					complexNumbers = new ArrayList<Complex>();
					continue;
				}
				
				getRoot.close();
				break;
			}
			
			Complex number = parseComplexNumber(root);
			
			System.out.println(number);
			
			if(number == null) {
				System.out.println("Write valid complex number");
				continue;
			}
			
			complexNumbers.add(number);
			i++;
		}
		
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(1, 0),
				complexNumbers.toArray(new Complex[complexNumbers.size()])); 

		Producer producer = new Producer(crp);
		
		FractalViewer.show(producer);
	}
	
	private static class Work implements Callable<Void> {
		
		int yMin;
		int yMax;
		int width;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		double reMin;
		double reMax;
		double imMax;
		double imMin;
		ComplexRootedPolynomial crp;
		short[] data;
		int height;
		int offset = 0;
		
		public Work(int yMin, int yMax, int width, int height, ComplexPolynomial polynomial,
				ComplexPolynomial derived, double reMin, double reMax, double imMax,
				double imMin, ComplexRootedPolynomial crp, short[] data) {
			this.yMax = yMax;
			this.yMin = yMin;
			this.width = width;
			this.derived = derived;
			this.polynomial = polynomial;
			this.imMax = imMax;
			this.imMin = imMin;
			this.reMin = reMin;
			this.reMax = reMax;
			this.height = height;
			this.crp = crp;
			this.data = data;
		}
		
		@Override
		public Void call() throws Exception {
			
			for(int k = yMin; k <= yMax; k++) {
			
				for(int l = 1; l <= width; l++) {
					
					offset = k * width + l - 1;
					
					double newRe = l * (reMax - reMin) / (width - 1) + reMin;
					double newIm = (height - 1 - k) * (imMax - imMin) / (height - 1) + imMin;
					
					Complex c = new Complex(newRe, newIm);
			    	Complex zn = c;
			    	Complex znold = zn;
			    	int iter = 0; 
			    	double module = 0;

			    	do {
			    		  Complex numerator = polynomial.apply(zn);
			    		  Complex denominator = derived.apply(zn);
			    		  Complex fraction = numerator.divide(denominator);
			    		  znold = zn.sub(fraction);
			    		  Complex helpModule = znold.sub(zn);
			    		  
			    		  module = helpModule.module();
			    		  zn = znold;
			    		  iter++;
			
			    	} while(iter < 100000 && module > 1E-3);

			    	int index = crp.indexOfClosestRootFor(zn, 0.002);
			    	
			    	data[offset] = (short) (index + 1);
				}
			}
			
			return null;
			
		}
	}
	
	private static class Producer implements IFractalProducer {
		ExecutorService executor;
		int size = 8 * Runtime.getRuntime().availableProcessors();
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		ComplexRootedPolynomial crp;
		
		public Producer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			polynomial = crp.toComplexPolynom();
			derived = polynomial.derive();
			
			executor = Executors.newFixedThreadPool(size, 
					new ThreadFactory() {
						
						@Override
						public Thread newThread(Runnable r) {
							Thread t = Executors.defaultThreadFactory().newThread(r);
			                t.setDaemon(true);
			                return t;
						}
					});
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			
			short[] data = new short[width * height];
			List<Future<Void>> futureObjects = new ArrayList<Future<Void>>();
			
			for(int i = 0; i < size; i++) {
				int yMin = i * height/size;
				int yMax = (i + 1) * height/size - 1;
				if(yMax > height - 1) {
					yMax = (height-1);
				}
				
				futureObjects.add(executor.submit(new Work(yMin, yMax, width,
						height, polynomial, derived, 
						reMin, reMax, imMax, imMin, crp, data)));
			}
			
			for(Future<Void> obj : futureObjects) {
				while(true) {
					try {
						obj.get();
						break;
					} catch (InterruptedException | ExecutionException e) {
						continue;
					}
				}
				
			}
			
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
	
	private static double getIm(String root, char operator) {
		// +1+1 to skip + and i
		if(root.indexOf('i') == root.length() - 1) {
			return Double.valueOf(1);
		} else {
			return Double.valueOf(root.substring(root.indexOf(operator) + 1 + 1));
		}
	}
	
	private static Complex parseComplexNumber(String root) {
		Complex complexNumber = new Complex();
		
		if(root == null) {
			return null;
		}
		
		if(root.contains("+") && root.indexOf("+") != 0) {
			double im = getIm(root, '+');
			
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('+'))), im);
					
			
					//str.indexOf("is", str.indexOf("is") + 1);
		} else if(root.contains("-") && root.indexOf("-") != 0) {
			double im = getIm(root, '-');
			
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('-'))), im);		
		
		} else if(root.indexOf("-") == 0) {
			double im = getIm(root, '-');
			
			complexNumber = new Complex(
					Double.valueOf(root.substring(0,
							root.indexOf('-', root.indexOf('-') + 1))), im);
			
		} else if(root.equals("0")) {
			complexNumber = new Complex(0, 0);
			
		} else if(root.equals("i")) {
			complexNumber = new Complex(0, 1);
		
		} else if(root.contains("i")) {
			complexNumber = new Complex(0, Double.valueOf(
					new StringBuilder().charAt(root.indexOf("i"))));
			
		} else {
			complexNumber = new Complex(Double.valueOf(root), 0);
		}
		
		return complexNumber;
	}
}
