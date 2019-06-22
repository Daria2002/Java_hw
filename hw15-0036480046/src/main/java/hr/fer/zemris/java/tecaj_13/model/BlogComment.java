package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="blog_comments")
/**
 * This class represents comment for blog.
 * @author Daria Matković
 *
 */
public class BlogComment {
	/** comment id **/
	private Long id;
	/** blog entry **/
	private BlogEntry blogEntry;
	/** users mail **/
	private String usersEMail;
	/** message **/
	private String message;
	/** date when comment is posted on **/
	private Date postedOn;
	
	@Id @GeneratedValue
	/**
	 * id getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Id setter
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	/**
	 * get blog entry
	 * @return blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * set blog entry
	 * @param blogEntry blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	@Column(length=100,nullable=false)
	/**
	 * get users mail
	 * @return users mail
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * set users mail
	 * @param usersEMail users mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	@Column(length=4096,nullable=false)
	/**
	 * gets message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * message setter
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	/**
	 * get date posted on
	 * @return date posted on
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * set date when comment is posted on
	 * @param postedOn date comment was pasted on
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}