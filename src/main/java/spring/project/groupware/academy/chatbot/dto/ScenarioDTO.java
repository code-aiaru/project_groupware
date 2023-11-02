package spring.project.groupware.academy.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.Selection;

import java.util.List;

@Data
@AllArgsConstructor
public class ScenarioDTO {
    private Scenario scenario;
    private List<Selection> selections;
}