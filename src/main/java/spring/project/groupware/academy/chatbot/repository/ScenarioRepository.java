package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.Scenario;

import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<Scenario, Integer>{

    Optional<Scenario> findById(Integer id);

    Scenario findBySelectionId(Integer previousSelectionId);

}
