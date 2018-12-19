package myapplication.mailserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "smtp.server")
public class SmtpServerProperties {
    private String softwareName = "Spring Boot SMTP Server";
    //private int port = 10025;
    private int port = 25;
    private int timeout = 120;
    
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
    
    
}
