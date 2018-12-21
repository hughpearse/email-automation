package myapplication.mailserver.repo;

import java.util.List;

public interface EmailRepositoryCustom {
	List<EmailSummarySubsetProjection> search(String query, String emailAddress, Integer page, Integer limit);
}
