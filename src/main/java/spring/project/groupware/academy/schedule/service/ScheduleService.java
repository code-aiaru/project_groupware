package spring.project.groupware.academy.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;
import spring.project.groupware.academy.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(ScheduleDTO::fromEntity) // Using static method from ScheduleDTO
                .collect(Collectors.toList());
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = scheduleDTO.toEntity(); // Using method from ScheduleDTO
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);
        return ScheduleDTO.fromEntity(savedSchedule); // Using static method from ScheduleDTO
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Update fields
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setStart(scheduleDTO.getStart());
        schedule.setEnd(scheduleDTO.getEnd());

        ScheduleEntity updatedSchedule = scheduleRepository.save(schedule);
        return ScheduleDTO.fromEntity(updatedSchedule); // Using static method from ScheduleDTO
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

}
