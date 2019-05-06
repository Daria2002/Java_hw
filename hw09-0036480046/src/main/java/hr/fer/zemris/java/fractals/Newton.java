package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;

public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
	
		Scanner getRoot = new Scanner(System.in);
		
		int i = 0;
		List<Complex> complexNumbers = new ArrayList<>();
		
		while (true) {
			System.out.printf("Root %d>", i);
			
			String root = getRoot.nextLine();
			
			if("done".equalsIgnoreCase(root)) {
				if(i < 1) {
					System.out.println("Enter at least 2 roots.");
					i = 0;
					complexNumbers = new ArrayList<Complex>();
					continue;
				}
				
				System.out.println("Image of fractal will appear shortly. Thank you.");
				
				class Producer implements IFractalProducer {

					ExecutorService executor;
					
					public Producer() {
						executor = Executors.newFixedThreadPool(
								8 * Runtime.getRuntime().availableProcessors(), 
								new ThreadFactory() {
									
									@Override
									public Thread newThread(Runnable r) {
										// TODO Auto-generated method stub
										return null;
									}
								});
					}
					
					@Override
					public void produce(double arg0, double arg1, double arg2, double arg3, int arg4, int arg5,
							long arg6, IFractalResultObserver arg7) {
						
						class Task implements Runnable {

						    public void run() {
						       System.out.println("MyRunnable running");
						    }
						}
						
						Task task = new Task();
						Future<String> future = (Future<String>) executor.submit(task);
						String result = null;
						try {
						    try {
								result = future.get();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
						    e.printStackTrace();
						}
						
					}
					
				}
				
				
				
				/*
				IFractalProducer producer = new IFractalProducer() {
					@Override
					public void produce(double arg0, double arg1, double arg2,
							double arg3, int arg4, int arg5, long arg6,
							IFractalResultObserver arg7) {

						// TODO Auto-generated method stub
						IFractalResultObserver observer = new IFractalResultObserver() {
							
							@Override
							public void acceptResult(short[] arg0, short arg1, long arg2) {
								// TODO Auto-generated method stub
								this.acceptResult(arg0, (short)(polynom.order()+1), i);
								
							}
						};
					}
				};*/
				
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

	private static Complex parseComplexNumber(String root) {
		Complex complexNumber = new Complex();
		
		if(root == null) {
			return null;
		}
		
		if(root.contains("+") && root.indexOf("+") != 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('+'))), 
					Double.valueOf(root.substring(root.indexOf('+') + 1)));
			
					//str.indexOf("is", str.indexOf("is") + 1);
		} else if(root.contains("-") && root.indexOf("-") != 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0, root.indexOf('-'))), 
					Double.valueOf(root.substring(root.indexOf('-') + 1)));		
		
		} else if(root.indexOf("-") == 0) {
			complexNumber = new Complex(
					Double.valueOf(root.substring(0,
							root.indexOf('-', root.indexOf('-') + 1))), 
					Double.valueOf(root.substring(
							root.indexOf('-', root.indexOf('-') + 1) + 1)));
			
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
