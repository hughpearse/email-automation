package myapplication.mailserver.repo;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailRepository extends JpaRepository<Email, Long>, PagingAndSortingRepository<Email, Long>, EmailRepositoryCustom {
	@Query("SELECT e FROM Email e WHERE e.id = ?1")
	Optional<Email> findById(Long id);

//	@Query(value="SELECT e FROM Email e WHERE inbox_Name = ?1 and is_Unread = TRUE")
//	Page<Email> findByInboxNameWithPagination(String emailAddress, Pageable pageable);
	
	@Query(value="SELECT e FROM Email e WHERE inbox_Name = ?1")
	Page<EmailSummarySubsetProjection> findByInboxNameWithPagination(String emailAddress, Pageable pageable);
	
}
