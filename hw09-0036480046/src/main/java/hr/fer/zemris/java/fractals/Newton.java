package hr.fer.zemris.java.fractals;

import java.awt.Dimension;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

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
				if(i < 2) {
					System.out.println("Enter at least 2 roots.");
					i = 0;
					complexNumbers = new ArrayList<Complex>();
					continue;
				}
				
				System.out.println("Image of fractal will appear shortly. Thank you.");
				
				Producer producer = new Producer(new ComplexRootedPolynomial(complexNumbers.get(0),
						complexNumbers.subList(1, complexNumbers.size()).toArray(new Complex[complexNumbers.size()-1])));
				
				producer.produce(1, 1, 1, 1, 1, 1, 1, new IFractalResultObserver() {
					
					@Override
					public void acceptResult(short[] arg0, short arg1, long arg2) {
						
					}
				});
				
				
				getRoot.close();
				break;
			}
			
			Complex number = parseComplexNumber(root);
			
			if(number == null) {
				System.out.println("Write valid complex number");
				continue;
			}
			
			complexNumbers.add(number);
			i++;
		}
	}

	private static class Job implements Callable {
	      private int i;
	      private int j;
	      private ComplexPolynomial polynomial;
	      private ComplexPolynomial derived;
	      private long maxIter;
	      private double convergenceTreshold;
	      private double rootThreshold;
	      private ComplexRootedPolynomial crp;
	      
	      public Job(int j, int i, ComplexPolynomial polynomial,
	    		  ComplexPolynomial derived, long maxIter, double convergenceTreshold, 
	    		  double rootThreshold, ComplexRootedPolynomial crp) {
	         this.i = i;
		     this.j = j;
		     this.polynomial = polynomial;
		     this.derived = derived;
		     this.maxIter = maxIter;
		     this.convergenceTreshold = convergenceTreshold;
		     this.rootThreshold = rootThreshold;
		     this.crp = crp;
	      }

	      @Override
	      public Object call() throws Exception {
	         return doJob(j, i, polynomial, derived, maxIter, convergenceTreshold, rootThreshold, crp);
	      }

	      private short doJob(int j, int i, ComplexPolynomial polynomial,
	    		  ComplexPolynomial derived, long maxIter, double convergenceTreshold, 
	    		  double rootThreshold, ComplexRootedPolynomial crp) throws InterruptedException {
	    	  
	    	  Complex c = new Complex(j, i);
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
	
	    	  } while(iter < maxIter && module > convergenceTreshold);

	    	  int index = crp.indexOfClosestRootFor(zn, rootThreshold);
	    	  return (short) (index + 1);   
	      }
		
	}
	
	private static class Producer implements IFractalProducer {
		short[] data;
		ExecutorService executor;
		int size = 8 * Runtime.getRuntime().availableProcessors();
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		int offset = 0;
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
		public void produce(double rootThreshold, double arg1, double arg2,
				double convergenceTreshold, int yMin, int yMax,
				long maxIter, IFractalResultObserver arg7) {
			
			for(int i = 0; i < size; i++){
				executor.execute(new Runnable() {
					@Override
					public void run() {
						
						for(int i = yMin; i < yMax; i++) {
							
							for(int j = 0; j < 1; j++) {
								Job job = new Job(j, yMin, polynomial, derived, maxIter, convergenceTreshold, rootThreshold, crp);
								Future<Integer> future = executor.submit(job);
								try {
									data[offset++] = future.get().shortValue();
								} catch (InterruptedException | ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				});
			}
			
			arg7.acceptResult(data, (short) 1, 1);
			
			
		}
		
	}
	
	private static Complex parseComplexNumber(String root) {
		Complex complexNumber = new Complex();
		
		if(root == null) {
			return null;
		}
		
		if(root.contains("+") && root.indexOf("+") != 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('+'))), 
					// +1+1 to skip + and i
					Double.valueOf(root.substring(root.indexOf('+') + 1 + 1)));
			
					//str.indexOf("is", str.indexOf("is") + 1);
		} else if(root.contains("-") && root.indexOf("-") != 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('-'))), 
					Double.valueOf(root.substring(root.indexOf('-') + 1 + 1)));		
		
		} else if(root.indexOf("-") == 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0,
							root.indexOf('-', root.indexOf('-') + 1))), 
					Double.valueOf(root.substring(
							root.indexOf('-', root.indexOf('-') + 1 + 1))));
			
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
