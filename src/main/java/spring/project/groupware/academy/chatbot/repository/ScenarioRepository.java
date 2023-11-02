package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Integer>{

    Scenario findBySelection_Id(int selectionId);

}
