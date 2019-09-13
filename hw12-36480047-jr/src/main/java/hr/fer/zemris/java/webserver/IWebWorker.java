package hr.fer.zemris.java.webserver;

/**
 * Interface that represents web worker
 * @author Daria Matković
 *
 */
public interface IWebWorker {
	/**
	 * This method processes request
	 * @param context request context
	 * @throws Exception throws exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
