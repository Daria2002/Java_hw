package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents Newton_Raphson iteration-based fractal.
 * @author Daria Matković
 *
 */
public class Newton {
	/**
	 * This method is executed when program is run. It takes roots entered via 
	 * command line and generate function for generating fractals.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
	
		Scanner getRoot = new Scanner(System.in);
		
		int i = 1;
		List<Complex> complexNumbers = new ArrayList<>();
		
		while (true) {
			System.out.printf("Root %d> ", i);
			
			String root = getRoot.nextLine();
			root = root.strip().replaceAll("\\s+", "");
			
			if("done".equalsIgnoreCase(root)) {
				if(i < 3) {
					System.out.println("Enter at least 2 roots.");
					System.exit(0);
				}
				
				getRoot.close();
				break;
			}
			Complex number;
			try {
				number = parseComplexNumber(root);
			} catch (Exception e) {
				System.out.println("Unable to parse given complex number");
				continue;
			}
			
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
	
	/**
	 * Nested class that implements Callable and represents job.
	 * @author Daria Matković
	 *
	 */
	private static class Work implements Callable<Void> {
		/** y minimum **/
		int yMin;
		/** y maximum **/
		int yMax;
		/** width **/
		int width;
		/** polynomial **/
		ComplexPolynomial polynomial;
		/** derived **/
		ComplexPolynomial derived;
		/** minimum real value **/
		double reMin;
		/** maximum real value **/
		double reMax;
		/** maximum imaginary value **/
		double imMax;
		/** minimum imaginary value **/
		double imMin;
		/** complex rooted polynomial **/
		ComplexRootedPolynomial crp;
		/** data **/
		short[] data;
		/** height **/
		int height;
		/** offset that represents data array index **/
		int offset = 0;
		
		/**
		 * Constructor that initialize local variables.
		 * @param yMin y minimum
		 * @param yMax y maximum
		 * @param width width 
		 * @param height height
		 * @param polynomial polynomial
		 * @param derived derived polynomial
		 * @param reMin minimum real value
		 * @param reMax maximum real value
		 * @param imMax maximum imaginary value 
		 * @param imMin minimum imaginary value
		 * @param crp complex rooted polynomial
		 * @param data array of indexes
		 */
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
			    		  znold = zn;
			    		  Complex fraction = numerator.divide(denominator);
			    		  zn = zn.sub(fraction);
			    		  module = znold.sub(zn).module();
			    		  iter++;
			
			    	} while(iter < 1000 && module > 0.001);

			    	int index = crp.indexOfClosestRootFor(zn, 0.002);

			    	data[offset] = (short) (index+1);
				}
			}
			
			return null;
		}
	}
	
	/**
	 * This class produce data for visualization of fractals. It implements 
	 * interface IFractalProducer that has method produce.
	 * @author Daria Matković
	 *
	 */
	private static class Producer implements IFractalProducer {
		/** executor **/
		ExecutorService executor;
		/** number of jobs **/
		int size = 8 * Runtime.getRuntime().availableProcessors();
		/** polynomial **/
		ComplexPolynomial polynomial;
		/** derived polynomial **/
		ComplexPolynomial derived;
		/** polynomial represented with roots **/
		ComplexRootedPolynomial crp;
		
		/**
		 * Constructor that initialize local variables
		 * @param crp complex rooted polynomial
		 */
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
				long requestNo, IFractalResultObserver observer, AtomicBoolean
				atomicBoolean) {
			
			short[] data = new short[width * height];
			List<Future<Void>> futureObjects = new ArrayList<Future<Void>>();
			
			for(int i = 0; i < size; i++) {
				int yMin = i * height/size;
				int yMax = (i + 1) * height/size - 1;
				if(yMax > height - 1) {
					yMax = (height-1);
				}
				
				futureObjects.add(executor.submit(new Work(yMin, yMax, width,
						height, polynomial, derived, reMin, reMax, imMax, imMin, crp, data)));
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
			
			observer.acceptResult(data, (short) polynomial.order(), requestNo);
		}
	}
	
	/**
	 * This method parse entered string to Complex number.
	 * @param root string that user entered
	 * @return complex number
	 */
	private static Complex parseComplexNumber(String root) {
		// complex number has only real component
		if(!root.contains("i")) {
			return new Complex(Double.valueOf(root), 0);
		}
		
		// complex number has only imaginary component
		else if(root.contains("i") && root.indexOf("i") < 2) {
			// complex number is i or -i
			if(!root.replace("i", "").matches(".*\\d.*")) {
				return new Complex(0, root.contains("-") ? -1 : 1);
			}
			
			return new Complex(0, Double.valueOf(root.replace("i", "")));
		}
	
		int helpIndex = root.substring(1).contains("+") ?
				root.indexOf("+", 1) : root.indexOf("-", 1);
		double re = Double.valueOf(root.substring(0, helpIndex));
		String imPart = root.substring(helpIndex + 1);
		
		if(!imPart.replace("i", "").matches(".*\\d.*")) {
			return new Complex(re, imPart.contains("-") ? -1 : 1);
		}
		
		return new Complex(re, Double.valueOf(Double.valueOf(imPart.replace("i", "")) *
				(root.substring(1).contains("+") ? 1:(-1))));
	}
}
