package qa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import qa.emailsender.Application;
import qa.emailsender.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EmailsenderApplicationTests {


	@Autowired
	private EmailService emailService;


	@Test
	public void testEmail(){
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
		emailService.sendMail("alice@example.com", "bob@example.com","Test subject",message);
	}

}
