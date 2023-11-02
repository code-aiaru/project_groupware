package spring.project.groupware.academy.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.dto.ScenarioDTO;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.repository.ScenarioRepository;
import spring.project.groupware.academy.chatbot.repository.SelectionRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final SelectionRepository selectionRepository;

    public Scenario getScenarioBySelectionId(Integer selectionId) {
        if (selectionId == 1) {
            return scenarioRepository.findById(1).orElse(null);
        } else {
            Selection selection = selectionRepository.findById(selectionId).orElse(null);
            if (selection != null) {
                return selection.getScenario();
            } else {
                return null;
            }
        }
    }
}
