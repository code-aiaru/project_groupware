package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Interest;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long>{

	Optional<Interest> findByKeyword(String keyword);

}
