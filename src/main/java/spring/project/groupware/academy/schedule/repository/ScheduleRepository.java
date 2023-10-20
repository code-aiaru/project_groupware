package spring.project.groupware.academy.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

}
