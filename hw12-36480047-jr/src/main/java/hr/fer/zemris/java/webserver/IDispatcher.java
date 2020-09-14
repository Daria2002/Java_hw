package hr.fer.zemris.java.webserver;

/**
 * Interface that represents dispatcher
 * @author Daria Matković
 *
 */
public interface IDispatcher {
	/**
	 * This class represents dispatch request
	 * @param urlPath path
	 * @throws Exception throws exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
