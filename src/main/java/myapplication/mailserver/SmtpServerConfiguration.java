package myapplication.mailserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.Collection;

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
            	Email aEmail = new Email(mailEnvelope.getSender().toString(), mailEnvelope.getRecipients().toString());
            	repository.save(aEmail);
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
