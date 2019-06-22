package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class represents JPADAO implementation that implements DAO
 * @author Daria Matković
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public boolean userExists(String username) {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.nickName", BlogUser.class)
				.setParameter("nickName", username).getResultList();
		return blogUsers != null && blogUsers.size() > 0;
	}

	@Override
	public boolean correctPassword(String nickName, String passwordHash) {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.nickName", BlogUser.class)
				.setParameter("nickName", nickName).getResultList();
		
		return blogUsers.get(0).getPasswordHash().equals(passwordHash);
	}

	@Override
	public void addNewUser(String firstName, String lastName, String email,
			String nickName, String passwordHash) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = new BlogUser();
		blogUser.setEmail(email);
		blogUser.setFirstName(firstName);
		blogUser.setLastName(lastName);
		blogUser.setNick(nickName);
		blogUser.setPasswordHash(passwordHash);
		
		em.persist(blogUser);
	}

	@Override
	public BlogUser getBlogUser(String nickName) {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.nickName", BlogUser.class)
				.setParameter("nickName", nickName).getResultList();
		return blogUsers.get(0);
	}

	@Override
	public List<BlogEntry> getEntries(String nick) {
		BlogUser blogUser = getBlogUser(nick);
		return blogUser.getBlogEntries();
	}

	@Override
	public BlogEntry getEntry(Long id) {
		//return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		List<BlogEntry> blogEntries = (List<BlogEntry>) JPAEMProvider.getEntityManager()
				.createQuery("select entry from BlogEntry as entry where entry.id=:id")
				.setParameter("id", id).getResultList();
		return blogEntries.get(0);
	}

	@Override
	public void addCommentToBlogUser(Long id, String commentText, String email) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogEntry entry = getEntry(id);
		 
		BlogComment newComment = new BlogComment();
		newComment.setBlogEntry(entry);
		newComment.setMessage(commentText);
		newComment.setPostedOn(new Date());
		newComment.setUsersEMail(email);
		 
		//entry.getComments().add(new BlogComment());
		 
		em.persist(newComment);
	}

	@Override
	public List<BlogUser> getRegistredUsers() {
		List<BlogUser> blogUsers = (List<BlogUser>)JPAEMProvider.getEntityManager()
				.createQuery("select user from BlogUser user").getResultList();
		return blogUsers;
	}

	@Override
	public void addNewEntry(BlogEntry newEntry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser creator = newEntry.getCreator();
		creator.getBlogEntries().add(newEntry);
		em.persist(newEntry);
	}
}