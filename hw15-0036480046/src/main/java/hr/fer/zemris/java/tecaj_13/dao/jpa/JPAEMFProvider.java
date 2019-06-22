package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class represents JPAEMF provider
 * @author Daria Matković
 *
 */
public class JPAEMFProvider {

	/** entity manager factory **/
	public static EntityManagerFactory emf;
	
	/**
	 * gets entity manager factory
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * sets entity manager factory
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}