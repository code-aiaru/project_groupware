package spring.project.groupware.academy.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.dto.ScenarioDTO;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.repository.ScenarioRepository;
import spring.project.groupware.academy.chatbot.repository.SelectionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final SelectionRepository selectionRepository;

//    public Scenario getScenarioBySelectionId(Integer previousSelectionId) {
//        // selectionId가 0이면, id가 1인 시나리오를 반환
//        if (previousSelectionId == 0) {
//            return scenarioRepository.findById(1)
//                    .orElseThrow(() -> new EntityNotFoundException("Scenario not found with id 1"));
//        } else {
//            return scenarioRepository.findBySelectionId(previousSelectionId);
//        }
//    }
    public ScenarioDTO getScenarioBySelectionId(Integer previousSelectionId) {
        Scenario scenario;

        if (previousSelectionId == null || previousSelectionId == 0) {
            scenario = scenarioRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("Scenario not found with id 1"));
        } else {
            scenario = scenarioRepository.findBySelectionId(previousSelectionId);
        }

        return ScenarioDTO.toDTO(scenario);
    }



}
