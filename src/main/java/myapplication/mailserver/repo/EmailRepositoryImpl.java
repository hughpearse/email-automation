package myapplication.mailserver.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//http://blog.netgloo.com/2014/11/23/spring-boot-and-hibernate-search-integration/
//https://tech.willhaben.at/how-to-add-full-text-search-and-faceting-to-any-database-511eea4a6c6
//https://www.baeldung.com/hibernate-search
//https://medium.com/@wkrzywiec/full-text-search-with-hibernate-search-lucene-part-1-e245b889aa8e
//

public class EmailRepositoryImpl implements EmailRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(EmailRepositoryImpl.class);
	
	public List<Email> search(String queryStr, String emailAddress, Integer page, Integer limit) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Email.class).get();
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("subjectText", "bodyText", "fromAddress").matching(queryStr).createQuery();
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Email.class);
		jpaQuery.setFirstResult(page);
		jpaQuery.setMaxResults(limit);
		
		@SuppressWarnings("unchecked")
        List results = jpaQuery.<Email>getResultList();
        if (results == null) {
            return new ArrayList();
        }
        return results;
		
//		log.info("querying database for: query={}", queryStr);
//		Query query = em.createNativeQuery("SELECT e FROM Email as e WHERE inbox_Name = ?1", EmailSummarySubsetProjection.class);
//		query.setParameter(1, emailAddress);
//		List<?> results = query.getResultList();
//		return results.isEmpty() ? null : (Page<EmailSummarySubsetProjection>)results;
	}

}
