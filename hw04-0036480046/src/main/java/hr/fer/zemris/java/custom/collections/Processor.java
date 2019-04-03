package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object capable of performing
 * some operation on the passed object.
 * @author Daria Matković
 *
 * @param <T> generic element type
 */
public interface Processor<T> {
	
	/** 
	 * This method is used for performing operations on given objects.
	 * Its implementation exists but does nothing.
	 * @param value value is given object of type Object 
	 */
	public abstract void process(T value);
}