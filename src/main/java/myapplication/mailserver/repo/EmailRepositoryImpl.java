package myapplication.mailserver.repo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.BasicTransformerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailRepositoryImpl implements EmailRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  EmailRepository repository;

  @SuppressWarnings("unchecked")
  public List<EmailSummarySubsetProjection> search(String queryStr, String emailAddress,
      Integer page, Integer limit) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    QueryBuilder queryBuilder =
        fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Email.class).get();

    Query combinedQuery = queryBuilder.bool()
        .must(queryBuilder.keyword().onField("inboxName").matching(emailAddress).createQuery())
        .must(queryBuilder.keyword().onFields("subjectText", "bodyText", "fromAddress")
            .matching(queryStr).createQuery())
        .createQuery();

    FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(combinedQuery, Email.class);
    jpaQuery.setFirstResult(page);
    jpaQuery.setMaxResults(limit);
    jpaQuery.setProjection("id");
    jpaQuery.setResultTransformer(new BasicTransformerAdapter() {
      private static final long serialVersionUID = 1L;

      @Override
      public Long transformTuple(Object[] tuple, String[] aliases) {
        return (Long) tuple[0];
      }
    });

    List<EmailSummarySubsetProjection> summaryList = new ArrayList<EmailSummarySubsetProjection>();
    List<Long> emailResults = jpaQuery.getResultList();
    for (Long l : emailResults) {
      summaryList.add(repository.findSummaryById(l));
    }

    return summaryList;
  }
}
