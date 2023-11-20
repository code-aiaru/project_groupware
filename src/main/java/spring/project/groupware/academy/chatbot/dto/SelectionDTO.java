package spring.project.groupware.academy.chatbot.dto;

import lombok.*;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.ScenarioResponseType;
import spring.project.groupware.academy.chatbot.entity.Selection;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class SelectionDTO {

    private Integer id;
    private String inform;
    private String selection;

    public static SelectionDTO toDTO(Selection selection) {
        if (selection == null) {
            return null;
        }

        SelectionDTO selectionDTO = new SelectionDTO();
        selectionDTO.setId(selection.getId());
        selectionDTO.setInform(selection.getInform());
        selectionDTO.setSelection(selection.getSelection());

        return selectionDTO;
    }
}