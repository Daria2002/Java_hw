package hr.fer.zemris.java.tecaj_13.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
/**
 * This class represents one blog entry.
 * @author Daria Matković
 *
 */
public class BlogEntry implements Serializable {
	/** id **/
	private Long id;
	/** comments **/
	private List<BlogComment> comments = new ArrayList<BlogComment>();
	/** date created at **/
	private Date createdAt;
	/** date entry last modified **/ 
	private Date lastModifiedAt;
	/** title **/
	private String title;
	/** text **/
	private String text;
	/** creator **/
	private BlogUser creator;

	@ManyToOne
	@JoinColumn(nullable=true)
	/**
	 * gets creator
	 * @return blog user that created blog entry
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * sets creator
	 * @param creator blog user
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}
	
	@Id @GeneratedValue
	/**
	 * gets id
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * sets id
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn DESC")
	/**
	 * gets comments 
	 * @return comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * sets comments
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	/**
	 * gets date when entry was created
	 * @return creation date
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * sets creation date
	 * @param createdAt date entry was created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	/**
	 * gets date entry was last modified at
	 * @return last modification date
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * sets last modification date
	 * @param lastModifiedAt last modification date
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	@Column(length=200,nullable=false)
	/**
	 * gets title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets title
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length=4096,nullable=false)
	/**
	 * gets text
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * sets text
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}