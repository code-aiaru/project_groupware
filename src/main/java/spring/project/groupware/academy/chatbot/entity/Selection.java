package spring.project.groupware.academy.chatbot.entity;

import lombok.*;
import spring.project.groupware.academy.chatbot.dto.SelectionDTO;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "selection")
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String inform; // 선택지 (텍스트)

    private String selection; // 선택지 (텍스트)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public static Selection toEntity(SelectionDTO selectionDTO) {
        Selection selection = new Selection();
        selection.setId(selectionDTO.getId());
        selection.setInform(selectionDTO.getInform());
        selection.setSelection(selectionDTO.getSelection());

        return selection;
    }

}
