package spring.project.groupware.academy.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scenario")
@Entity
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sequence; // 시나리오 순서

    private String anouncement; // 안내 메세지 (텍스트)

    private String scenarioFor; // 어떤 선택지인지

    @Enumerated(EnumType.STRING)
    private ScenarioResponseType scenarioResponseType; // 어떤 형태로 응답을 받을건지? (TEXT, VALUE)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_selection_id")
    private Selection selection;

}
