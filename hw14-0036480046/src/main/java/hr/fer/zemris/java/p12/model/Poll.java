package hr.fer.zemris.java.p12.model;

public class Poll {

	private long pollId;
	private String title;
	private String message;
	
	public Poll() {
		
	}

	public Poll(long pollId, String title, String message) {
		super();
		this.pollId = pollId;
		this.title = title;
		this.message = message;
	}

	public long getPollId() {
		return pollId;
	}

	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}