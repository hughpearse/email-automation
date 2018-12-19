package myapplication.mailserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "smtp.server")
@Data
public class SmtpServerProperties {
    private String softwareName = "Spring Boot SMTP Server";
    //private int port = 10025;
    private int port = 25;
    private int timeout = 120;
}
