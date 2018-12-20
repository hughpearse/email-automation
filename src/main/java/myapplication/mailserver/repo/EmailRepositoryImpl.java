package myapplication.mailserver.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class EmailRepositoryImpl implements EmailRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(EmailRepositoryImpl.class);
	
	@Override
	public Iterable<Long> listInboxForUser(String emailAddress) {
		log.info("querying database for: inboxName={}", emailAddress);
		Query query = em.createNativeQuery("SELECT id FROM Email WHERE inbox_Name = ?1 and is_Unread = ?2");
		query.setParameter(1, emailAddress);
		query.setParameter(2, true);
		List<?> results = query.getResultList();
		return results.isEmpty() ? null : (Iterable<Long>)results;
	}
	
	@Override
	public Email readEmailByID(long id) {
		log.info("querying database for: id={}", id);
		Query query = em.createNativeQuery("SELECT * FROM Email WHERE id = :id", Email.class);
		query.setParameter("id", id);
		Email result = (Email) query.getSingleResult();
		return result.getId() != id ? null : result;
	}
}
