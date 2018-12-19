package myapplication.mailserver.repo;

public interface EmailRepositoryCustom {
	public Iterable<Email> findByToContains(String to);
	public Iterable<Email> findByFromContains(String from);
}
