package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Intention;
import spring.project.groupware.academy.chatbot.entity.Interest;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long>{

//	Optional<Interest> findByNameAndUpper(String token, Interest upper);

//	Optional<Interest> findByName(String token);
//	Optional<Interest> findByNameAndUpperIsNull(String token);

	Optional<Interest> findByKeyword(String keyword);

}
