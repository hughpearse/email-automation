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
    
    @RequestMapping("/searchByRecepient")
    public Iterable<Email> findbyto(@RequestParam(value="toAddress") String emailAddress){
    	log.info("processing /search for: toAddress={}", emailAddress);
    	return repository.findByToContains(emailAddress);
    }
    
    @RequestMapping("/searchBySender")
    public Iterable<Email> findbyfrom(@RequestParam(value="fromAddress") String emailAddress){
    	log.info("processing /search for: fromAddress={}", emailAddress);
    	return repository.findByFromContains(emailAddress);
    }
    
    @RequestMapping("/inbox")
    public Iterable<Email> findbyInbox(@RequestParam(value="emailAddress") String emailAddress){
    	log.info("processing /inbox for: emailAddress={}", emailAddress);
    	return repository.listInboxForUser(emailAddress);
    }
    
}
