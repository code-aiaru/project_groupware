package spring.project.groupware.academy.schedule.entity;

import lombok.Getter;
import lombok.Setter;
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
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;

}
