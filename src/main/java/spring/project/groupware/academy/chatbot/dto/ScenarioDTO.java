package spring.project.groupware.academy.chatbot.dto;

import lombok.*;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.ScenarioResponseType;
import spring.project.groupware.academy.chatbot.entity.Selection;

import javax.persistence.*;
import java.util.List;

@Data
public class ScenarioDTO {

    private Integer id;
    private Integer sequence;
    private String inform;
    private String scenarioFor;
    private ScenarioResponseType scenarioResponseType;

    public static ScenarioDTO toDTO(Scenario scenario) {
        if (scenario == null) {
            return null;
        }

        ScenarioDTO scenarioDTO = new ScenarioDTO();
        scenarioDTO.setId(scenario.getId());
        scenarioDTO.setSequence(scenario.getSequence());
        scenarioDTO.setInform(scenario.getInform());
        scenarioDTO.setScenarioFor(scenario.getScenarioFor());
        scenarioDTO.setScenarioResponseType(scenario.getScenarioResponseType());

        return scenarioDTO;
    }
}