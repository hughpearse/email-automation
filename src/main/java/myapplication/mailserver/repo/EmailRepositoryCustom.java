package myapplication.mailserver.repo;

public interface EmailRepositoryCustom {
	public Iterable<Long> listInboxForUser(String emailAddress);
	public Email readEmailByID(long id);
}
