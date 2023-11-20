package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Selection;

import java.util.List;

public interface SelectionRepository extends JpaRepository<Selection, Integer>{

    List<Selection> findAllByScenarioId(int scenarioId);

}
