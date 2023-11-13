package spring.project.groupware.academy.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.dto.SelectionDTO;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.repository.SelectionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SelectionService {

    private final SelectionRepository selectionRepository;

    public List<SelectionDTO> getSelectionsByScenarioId(int scenarioId) {
        List<Selection> selections = selectionRepository.findAllByScenarioId(scenarioId);
        return selections.stream().map(SelectionDTO::toDTO).collect(Collectors.toList());
    }
}
