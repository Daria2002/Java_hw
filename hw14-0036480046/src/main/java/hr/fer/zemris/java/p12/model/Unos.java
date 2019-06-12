package hr.fer.zemris.java.p12.model;

/**
 * This class represents entry
 * @author Daria Matković
 *
 */
public class Unos {
	/** id **/
	private long id;
	/** title **/
	private String title;
	/** description **/
	private String desc;
	/** poll id **/
	private long pollId;
	/** votes **/
	private long votes;
	
	/**
	 * Constructor that doesn't do anything
	 */
	public Unos() {
		
	}
	
	/**
	 * Constructor that initialize id, title, desc, pollid, votes
	 * @param id id
	 * @param title title
	 * @param desc description
	 * @param pollId poll id
	 * @param votes votes
	 */
	public Unos(long id, String title, String desc, long pollId, long votes) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.pollId = pollId;
		this.votes = votes;
	}
	
	/**
	 * id getter
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * id setter
	 * @param id id
	 */
	public void setId(long id) {
		this.id = id;
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
	 * description getter
	 * @return description
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * description setter
	 * @param desc description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	
	/**
	 * votes getter
	 * @return votes
	 */
	public long getVotes() {
		return votes;
	}
	
	/**
	 * votes setter 
	 * @param votes votes
	 */
	public void setVotes(long votes) {
		this.votes = votes;
	}
}
