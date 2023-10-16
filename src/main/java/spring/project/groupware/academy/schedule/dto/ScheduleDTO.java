package spring.project.groupware.academy.schedule.dto;

import lombok.*;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createTime; // from BaseEntity
    private LocalDateTime updateTime; // from BaseEntity

    // getters and setters

    public static ScheduleDTO fromEntity(ScheduleEntity scheduleEntity) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(scheduleEntity.getId());
        dto.setTitle(scheduleEntity.getTitle());
        dto.setStart(scheduleEntity.getStart());
        dto.setEnd(scheduleEntity.getEnd());
        dto.setCreateTime(scheduleEntity.getCreateTime());
        dto.setUpdateTime(scheduleEntity.getUpdateTime());
        return dto;
    }

    public ScheduleEntity toEntity() {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(this.id);
        scheduleEntity.setTitle(this.title);
        scheduleEntity.setStart(this.start);
        scheduleEntity.setEnd(this.end);
        // Note: createTime and updateTime should be managed by JPA, not set manually
        return scheduleEntity;
    }

}
