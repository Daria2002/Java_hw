package hr.fer.zemris.java.p12.model;

/**
 * This class describes poll.
 * @author Daria Matković
 *
 */
public class Poll {

	/** poll id **/
	private long pollId;
	/** poll title **/
	private String title;
	/** poll message **/
	private String message;
	
	/**
	 * Constructor that doesn't do anything
	 */
	public Poll() {
		
	}

	/**
	 * Constructor that initialize pollId, title and message
	 * @param pollId poll id
	 * @param title title
	 * @param message message
	 */
	public Poll(long pollId, String title, String message) {
		super();
		this.pollId = pollId;
		this.title = title;
		this.message = message;
	}

	/**
	 * poll id getter
	 * @return poll id
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * poll id setter
	 * @param pollId poll id
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	/**
	 * title getter
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * title setter
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * message getter
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * message setter
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}