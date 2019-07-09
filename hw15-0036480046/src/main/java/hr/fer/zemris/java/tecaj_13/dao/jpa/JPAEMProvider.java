package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.p12.jpdao.JPDAOException;

/**
 * THis class represents JPAEM provider
 * @author Daria Matković
 *
 */
public class JPAEMProvider {

	/** locals **/
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * gets entity manager
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * close method 
	 * @throws DAOException exception
	 */
	public static void close() throws JPDAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		JPDAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new JPDAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new JPDAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}