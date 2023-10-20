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
    private Boolean isAllDay;
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
        scheduleEntity.setIsAllDay(scheduleDTO.getIsAllDay());
        scheduleEntity.setColor(scheduleDTO.getColor());

        if (scheduleDTO.getEmployeeEntity() != null) {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setEmployeeNo(scheduleDTO.getEmployeeEntity().getEmployeeNo());
            scheduleEntity.setEmployeeEntity(employeeEntity);
        }

        return scheduleEntity;
    }

    public void updateFromDTO(ScheduleDTO scheduleDTO) {
        this.setTitle(scheduleDTO.getTitle());
        this.setTarget(scheduleDTO.getTarget());
        this.setStart(scheduleDTO.getStart());
        this.setEnd(scheduleDTO.getEnd());
        this.setIsAllDay(scheduleDTO.getIsAllDay());
        this.setColor(scheduleDTO.getColor());
        // 다른 필요한 필드도 여기서 업데이트할 수 있습니다.
    }



}
