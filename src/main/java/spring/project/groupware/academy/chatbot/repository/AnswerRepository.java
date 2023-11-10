package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.project.groupware.academy.chatbot.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

    // 추가할 메서드
    @Query("SELECT a FROM Answer a WHERE :message LIKE CONCAT('%', a.triggerKeyword, '%')")
    List<Answer> findByTriggerKeywordInMessage(@Param("message") String message);

}
