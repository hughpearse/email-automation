package myapplication.mailserver.rest;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myapplication.mailserver.repo.Email;
import myapplication.mailserver.repo.EmailRepository;
import myapplication.mailserver.repo.EmailSummarySubsetProjection;

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
    public Page<EmailSummarySubsetProjection> findbyInbox(
    		@RequestParam(value="emailAddress") String emailAddress,
    		@RequestParam(value="page", required=false) Optional<Integer> pageOpt,
    		@RequestParam(value="limit", required=false) Optional<Integer> limitOpt,
    		@RequestParam(value="sort", required=false) Optional<String> sortOpt
    		){
    	log.info("processing /inbox for: emailAddress={}", emailAddress);
    	Integer page = pageOpt.orElse(0);
    	Integer limit = limitOpt.orElse(10);
    	String sortStr = sortOpt.orElse("DESC");
    	Sort sort = new Sort(Sort.Direction.fromString(sortStr), new String[]{"id"});
    	Pageable pageable = PageRequest.of(page, limit, sort);
    	return repository.findByInboxNameWithPagination(emailAddress, pageable);
    }
    
    @RequestMapping("/open")
    public Email openEmailByID(@RequestParam(value="id") long id){
    	log.info("processing /open email: id={}", id);
    	Optional<Email> anEmail = repository.findById(id);
    	if(anEmail.isPresent()) {
    		Email email = anEmail.get();
    		email.setIsUnread(false);
    		repository.save(email);
    	}
		return anEmail.get();
    }
    
    @RequestMapping("/delete")
    public void deleteEmailByID(@RequestParam(value="id") long id){
    	log.info("processing /delete email: id={}", id);
    	Optional<Email> anEmail = repository.findById(id);
    	if(anEmail.isPresent()) {
    		repository.deleteById(id);
    	}
    }
}
