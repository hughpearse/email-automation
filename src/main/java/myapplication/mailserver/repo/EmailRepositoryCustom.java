package myapplication.mailserver.repo;

import java.util.List;

public interface EmailRepositoryCustom {
	List<Email> search(String query, String emailAddress);
}
