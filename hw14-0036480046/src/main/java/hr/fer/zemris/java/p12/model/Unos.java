package hr.fer.zemris.java.p12.model;

public class Unos {

	private long id;
	private String title;
	private String desc;
	private long pollId;
	private long votes;
	
	public Unos() {
		
	}
	
	public Unos(long id, String title, String desc, long pollId, long votes) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.pollId = pollId;
		this.votes = votes;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public long getVotes() {
		return votes;
	}
	public void setVotes(long votes) {
		this.votes = votes;
	}
}
