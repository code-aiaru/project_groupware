package spring.project.groupware.academy.chatbot.entity;

import lombok.*;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.chatbot.dto.ScenarioDTO;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "scenario")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sequence; // 시나리오 순서

    private String inform; // 안내 메세지 (텍스트)

    private String scenarioFor; // 어떤 선택지인지

    @Enumerated(EnumType.STRING)
    private ScenarioResponseType scenarioResponseType; // 어떤 형태로 응답을 받을건지? (TEXT, VALUE)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_selection_id")
    private Selection selection;

    public static Scenario toEntity(ScenarioDTO scenarioDTO) {
        Scenario scenario = new Scenario();
        scenario.setId(scenarioDTO.getId());
        scenario.setSequence(scenarioDTO.getSequence());
        scenario.setInform(scenarioDTO.getInform());
        scenario.setScenarioFor(scenarioDTO.getScenarioFor());

        return scenario;
    }
}
