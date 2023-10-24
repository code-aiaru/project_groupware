package spring.project.groupware.academy.schedule.dto;

import lombok.*;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Integer id;
    private String title;
    private String target;
    private String start;
    private String end;
    private Boolean isAllDay;
    private String color;

    private LocalDateTime createTime; // from BaseEntity
    private LocalDateTime updateTime; // from BaseEntity

    private EmployeeEntity employeeEntity;
    private Long writer; // Add this line

    // getters and setters

    public static ScheduleDTO toDTO(ScheduleEntity scheduleEntity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(scheduleEntity.getId());
        scheduleDTO.setTitle(scheduleEntity.getTitle());
        scheduleDTO.setTarget(scheduleEntity.getTarget());
        scheduleDTO.setStart(scheduleEntity.getStart());
        scheduleDTO.setEnd(scheduleEntity.getEnd());
        scheduleDTO.setIsAllDay(scheduleEntity.getIsAllDay());
        scheduleDTO.setColor(scheduleEntity.getColor());
        scheduleDTO.setCreateTime(scheduleEntity.getCreateTime());
        scheduleDTO.setUpdateTime(scheduleEntity.getUpdateTime());

        if (scheduleEntity.getEmployeeEntity() != null) {
            scheduleDTO.setWriter(scheduleEntity.getEmployeeEntity().getEmployeeNo());
        }

        return scheduleDTO;
    }



}
