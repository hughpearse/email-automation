package myapplication.mailserver.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myapplication.mailserver.repo.Email;
import myapplication.mailserver.repo.EmailRepository;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	EmailRepository repository;
	
	private static final Logger log = LoggerFactory.getLogger(EmailController.class);
	
	@RequestMapping("/all")
    public Iterable<Email> listemails(){
    	log.info("processing /all");
        return repository.findAll();
    }
    
    @RequestMapping("/inbox")
    public Iterable<Long> findbyInbox(@RequestParam(value="emailAddress") String emailAddress){
    	log.info("processing /inbox for: emailAddress={}", emailAddress);
    	return repository.listInboxForUser(emailAddress);
    }
    
    @RequestMapping("/open")
    public Email openEmailByID(@RequestParam(value="id") long id){
    	log.info("processing /open email: id={}", id);
    	Email anEmail = repository.readEmailByID(id);
    	anEmail.setIsUnread(false);
		repository.save(anEmail);
		return anEmail;
    }
    
}
