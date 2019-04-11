package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object capable of performing
 * some operation on the passed object.
 * @author Daria Matkovic
 *
 */
public interface Processor {
	
	/** 
	 * This method is used for performing operations on given objects.
	 * Its implementation exists but does nothing.
	 * @param value value is given object of type Object 
	 */
	void process(Object value);
} 
