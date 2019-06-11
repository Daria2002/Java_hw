package hr.fer.zemris.java.p12.model;

public class Poll {

	private int pollId;
	private String title;
	private String message;
	
	public Poll() {
		
	}

	public Poll(int pollId, String title, String message) {
		super();
		this.pollId = pollId;
		this.title = title;
		this.message = message;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
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