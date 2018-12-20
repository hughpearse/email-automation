package myapplication.mailserver.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//http://blog.netgloo.com/2014/11/23/spring-boot-and-hibernate-search-integration/
//https://tech.willhaben.at/how-to-add-full-text-search-and-faceting-to-any-database-511eea4a6c6
//https://www.baeldung.com/hibernate-search
//https://medium.com/@wkrzywiec/full-text-search-with-hibernate-search-lucene-part-1-e245b889aa8e
//

public class EmailRepositoryImpl implements EmailRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(EmailRepositoryImpl.class);
	

}
