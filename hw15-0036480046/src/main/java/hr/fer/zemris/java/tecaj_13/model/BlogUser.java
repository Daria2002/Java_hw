package hr.fer.zemris.java.tecaj_13.model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="BlogUser.nickName",query="select b from BlogUser as b where b.nick=:nickName")
})
@Entity
@Table(name = "blog_users")
/**
 * This class represents blog user
 * @author Daria Matković
 *
 */
public class BlogUser {
	/** id **/
	private Long id;
	/** first name **/
	private String firstName;
	/** last name **/
	private String lastName;
	/** nick name **/
	private String nick;
	/** email **/
	private String email;
	/** password hash **/
	private String passwordHash;
	/** blog entries **/
	private List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("lastModifiedAt DESC")
	/**
	 * gets blog entries
	 * @return list of blog entries
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	
	/**
	 * sets blog entries
	 * @param blogEntries list of blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	@Id
	@GeneratedValue
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

	@Column(length = 100, nullable = false)
	/**
	 * gets first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * sets first name
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 100, nullable = false)
	/**
	 * gets last name
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * sets last name
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 100, nullable = false)
	@JoinColumn(unique = true)
	/**
	 * gets nick name
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * sets nick name
	 * @param nick nick name
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Column(length = 100, nullable = false)
	/**
	 * gets email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * sets email
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 100, nullable = false)
	/**
	 * gets password hash
	 * @return password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * sets password hash
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
