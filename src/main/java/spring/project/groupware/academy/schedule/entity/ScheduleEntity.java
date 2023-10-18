package spring.project.groupware.academy.schedule.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.util.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "schedule")
public class ScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String target;
    private String start;
    private String end;
    private Boolean allDay;
    private String color;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private EmployeeEntity employeeEntity;

    public static ScheduleEntity toEntity(ScheduleDTO scheduleDTO){
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(scheduleDTO.getId());
        scheduleEntity.setTitle(scheduleDTO.getTitle());
        scheduleEntity.setTarget(scheduleDTO.getTarget());
        scheduleEntity.setStart(scheduleDTO.getStart());
        scheduleEntity.setEnd(scheduleDTO.getEnd());
        scheduleEntity.setAllDay(scheduleDTO.getAllDay());
        scheduleEntity.setColor(scheduleDTO.getColor());

        if (scheduleDTO.getEmployeeEntity() != null) {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setEmployeeNo(scheduleDTO.getEmployeeEntity().getEmployeeNo());
            scheduleEntity.setEmployeeEntity(employeeEntity);
        }

        return scheduleEntity;
    }




}
