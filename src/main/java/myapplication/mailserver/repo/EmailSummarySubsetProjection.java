package myapplication.mailserver.repo;

public interface EmailSummarySubsetProjection {
	Long getId();
	String getFromAddress();
	String getSubjectText();
	String getTimestampRecieved();
	Boolean getIsUnread();
}
