package myapplication.mailserver.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class BuildSearchIndex implements ApplicationListener<ContextRefreshedEvent> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger log = LoggerFactory.getLogger(BuildSearchIndex.class);
	
	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		try {
	      FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
	      fullTextEntityManager.createIndexer().startAndWait();
	    }
	    catch (InterruptedException e) {
	      log.error("An error occurred trying to build the serach index: " + e.toString());
	    }
	    return;
	}

}
