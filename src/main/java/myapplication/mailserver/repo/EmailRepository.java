package myapplication.mailserver.repo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long>, EmailRepositoryCustom {

}
