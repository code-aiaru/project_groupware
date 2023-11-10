package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

    List<Answer> findByTriggerKeywordContaining(String triggerKeyword);


}
