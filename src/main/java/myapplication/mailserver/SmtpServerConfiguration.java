package myapplication.mailserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.MessageHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import myapplication.mailserver.repo.Email;
import myapplication.mailserver.repo.EmailRepository;

@Configuration
@EnableConfigurationProperties(SmtpServerProperties.class)
public class SmtpServerConfiguration {

  private static final Logger log = LoggerFactory.getLogger(SmtpServerConfiguration.class);

  @Autowired
  EmailRepository repository;

  @Bean(initMethod = "start", destroyMethod = "stop")
  public SmtpServer smtpServer(SmtpServerProperties properties,
      Collection<ProtocolHandler> handlers) {
    return new SmtpServer(properties, handlers);
  }

  @Bean
  public ProtocolHandler loggingMessageHook() {
    return new MessageHook() {
      @Override
      public HookResult onMessage(SMTPSession smtpSession, MailEnvelope mailEnvelope) {
        String[] inboxArr =
            mailEnvelope.getRecipients().toString().replace("[", "").replaceAll("]", "").split(",");

        for (String inboxName : inboxArr) {
          String from = mailEnvelope.getSender().toString();
          String to = mailEnvelope.getRecipients().toString();
          Email aEmail = new Email(from, to, inboxName);

          SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
          Timestamp timestamp = new Timestamp(System.currentTimeMillis());
          aEmail.setTimestampRecieved(sdf.format(timestamp));

          try {
            String rawEmail =
                IOUtils.toString(mailEnvelope.getMessageInputStream(), StandardCharsets.UTF_8);

            Pattern patSub = Pattern.compile(".*Subject:\\s*(.*)");
            Matcher matSub = patSub.matcher(rawEmail);
            while (matSub.find()) {
              String[] subjectArr = matSub.group().split(":");
              String subject =
                  String.join(":", Arrays.copyOfRange(subjectArr, 1, subjectArr.length));
              subject = subject.replaceFirst(" ", "");
              aEmail.setSubjectText(subject);
              log.info("Subject: {}", subject);
            }

            Pattern patBod = Pattern.compile("\\r\\n\\r\\n(.*)");
            Matcher matBod = patBod.matcher(rawEmail);
            while (matBod.find()) {
              String body = matBod.group();
              body = body.replaceFirst("\\r\\n\\r\\n", "");
              aEmail.setBodyText(body);
              log.info("Body: {}", body);
            }

            aEmail.setBccAddressList("");
            aEmail.setCcAddressList("");

            byte[] encodedBytes = Base64.getEncoder().encode(rawEmail.getBytes());
            String base64RawEmail = new String(encodedBytes);
            aEmail.setRawEmail(base64RawEmail);
            log.info("Base64 email: {}", base64RawEmail);
          } catch (IOException e) {
            e.printStackTrace();
          }
          repository.save(aEmail);
        } // end for

        log.info("mail from={} to={} size={}", mailEnvelope.getSender(),
            mailEnvelope.getRecipients(), mailEnvelope.getSize());
        return HookResult.OK;
      }

      @Override
      public void init(org.apache.commons.configuration.Configuration configuration) {}

      @Override
      public void destroy() {}
    };
  }
}
