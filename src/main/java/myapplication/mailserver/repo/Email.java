package myapplication.mailserver.repo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Email")
public class Email implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(max = 100)
	@NotNull
	@Column(length = 100, unique = false, nullable = false)
	private String fromAddress;
	@Size(max = 1000)
	@NotNull
	@Column(length = 1000, unique = false, nullable = false)
	private String toAddress;
	@Size(max = 256)
	@NotNull
	@Column(length = 256, unique = false, nullable = false)
	private String inboxName;
	
	@Size(max = 100)
	@Column(length = 100, unique = false)
	private String ccAddressList;
	@Size(max = 100)
	@Column(length = 100, unique = false)
	private String bccAddressList;
	@Size(max = 100)
	@Column(length = 100, unique = false)
	private String subjectText;
	@Size(max = 1000)
	@Column(length = 1000, unique = false)
	private String bodyText;
	@Size(max = 100)
	@Column(length = 100, unique = false)
	private String timestampRecieved;
	@Size(max = 2000)
	@Column(length = 2000, unique = false)
	private String rawEmail;
	
	protected Email() {}
	
	public Email(String from, String to, String inboxName) {
		setFromAddress(from);
		setToAddress(to);
		setInboxName(inboxName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	

	public String getInboxName() {
		return inboxName;
	}

	public void setInboxName(String inboxName) {
		this.inboxName = inboxName;
	}

	public String getCcAddressList() {
		return ccAddressList;
	}

	public void setCcAddressList(String ccAddressList) {
		this.ccAddressList = ccAddressList;
	}

	public String getBccAddressList() {
		return bccAddressList;
	}

	public void setBccAddressList(String bccAddressList) {
		this.bccAddressList = bccAddressList;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getTimestampRecieved() {
		return timestampRecieved;
	}

	public void setTimestampRecieved(String timestampRecieved) {
		this.timestampRecieved = timestampRecieved;
	}

	public String getRawEmail() {
		return rawEmail;
	}

	public void setRawEmail(String rawEmail) {
		this.rawEmail = rawEmail;
	}
	
}
