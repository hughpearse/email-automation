package myapplication.mailserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.MessageHook;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myapplication.mailserver.repo.Email;
import myapplication.mailserver.repo.EmailRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableConfigurationProperties(SmtpServerProperties.class)
public class SmtpServerConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(SmtpServerConfiguration.class);
	
	@Autowired
	EmailRepository repository;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SmtpServer smtpServer(SmtpServerProperties properties, Collection<ProtocolHandler> handlers) {
        return new SmtpServer(properties, handlers);
    }

    @Bean
    public ProtocolHandler loggingMessageHook() {
        return new MessageHook() {
            @Override
            public HookResult onMessage(SMTPSession smtpSession, MailEnvelope mailEnvelope) {
            	String[] inboxArr = mailEnvelope.getRecipients().toString().replace("[", "").replaceAll("]", "").split(",");
            	
            	for (String inboxName : inboxArr) {
            		String from = mailEnvelope.getSender().toString();
            		String to = mailEnvelope.getRecipients().toString();
            		Email aEmail = new Email(from, to, inboxName);
                	
                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                	aEmail.setTimestampRecieved(sdf.format(timestamp));
                	
                	try {
                		String rawEmail = IOUtils.toString(mailEnvelope.getMessageInputStream(), Charsets.UTF_8);
                		
                		
                		Pattern patSub = Pattern.compile(".*Subject:\\s*(.*)");
                		Matcher matSub = patSub.matcher(rawEmail);
                		while (matSub.find()) {
                			String[] subjectArr = matSub.group().split(":");
                			String subject = String.join(":", Arrays.copyOfRange(subjectArr, 1, subjectArr.length));
                    		aEmail.setSubjectText(subject);
                    		log.info("Subject: {}", subject);
                		}
                		
                		byte[] encodedBytes = Base64.getEncoder().encode(rawEmail.getBytes());
                		String base64RawEmail = new String(encodedBytes);
                		aEmail.setRawEmail(base64RawEmail);
    					log.info("Base64 email: {}", base64RawEmail);
    					/*
    					Received: from localhost (EHLO localhost) ([127.0.0.1])
    					          by hughs-mbp.mul.ie.ibm.com (Spring Boot SMTP Server) with ESMTP ID -1533514914
    					          for <kiranreddy2004@gmail.com>;
    					          Wed, 19 Dec 2018 16:55:51 +0000 (GMT)
    					Date: Wed, 19 Dec 2018 16:55:51 +0000 (GMT)
    					From: admin@admin.com
    					To: kiranreddy2004@gmail.com
    					Message-ID: <1535660266.0.1545238551385@localhost>
    					Subject: Test subject
    					MIME-Version: 1.0
    					Content-Type: text/plain; charset=UTF-8
    					Content-Transfer-Encoding: 7bit
    					
    					Test mail
    					 */
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
                	repository.save(aEmail);
            	}//end for
            	
                log.info("mail from={} to={} size={}", mailEnvelope.getSender(), mailEnvelope.getRecipients(), mailEnvelope.getSize());
                return HookResult.OK;
            }

            @Override
            public void init(org.apache.commons.configuration.Configuration configuration) {
            }

            @Override
            public void destroy() {
            }
        };
    }
}
