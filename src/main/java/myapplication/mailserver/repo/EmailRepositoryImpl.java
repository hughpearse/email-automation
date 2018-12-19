package myapplication.mailserver.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailRepositoryImpl implements EmailRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(EmailRepositoryImpl.class);
	
	@Override
	public Iterable<Email> findByToContains(String emailAddress) {
		log.info("querying database for: toAddress={}", emailAddress);
		Query query = em.createNativeQuery("SELECT * FROM Email WHERE Email.to_Address LIKE :emailAddress", Email.class);
		query.setParameter("emailAddress", "%"+emailAddress+"%");
		List<?> results = query.getResultList();
		return results.isEmpty() ? null : (Iterable<Email>)results;
	}
	
	@Override
	public Iterable<Email> findByFromContains(String emailAddress) {
		log.info("querying database for: fromAddress={}", emailAddress);
		Query query = em.createNativeQuery("SELECT * FROM Email WHERE Email.from_Address = :emailAddress", Email.class);
		query.setParameter(1, emailAddress);
		List<?> results = query.getResultList();
		return results.isEmpty() ? null : (Iterable<Email>)results;
	}
	
	@Override
	public Iterable<Email> listInboxForUser(String emailAddress) {
		log.info("querying database for: inboxName={}", emailAddress);
		Query query = em.createNativeQuery("SELECT * FROM Email WHERE inbox_Name = :emailAddress", Email.class);
		query.setParameter("emailAddress", emailAddress);
		List<?> results = query.getResultList();
		return results.isEmpty() ? null : (Iterable<Email>)results;
	}
}
