package spring.project.groupware.academy.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.repository.SelectionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectionService {

    private final SelectionRepository selectionRepository;

    public List<Selection> getSelectionsByScenarioId(int scenarioId) {
        return selectionRepository.findAllByScenarioId(scenarioId);
    }
}
