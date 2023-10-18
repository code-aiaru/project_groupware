package spring.project.groupware.academy.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;
import spring.project.groupware.academy.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleDTO saveEvent(ScheduleDTO scheduleDTO, EmployeeEntity employeeEntity) {
        ScheduleEntity scheduleEntity = ScheduleEntity.toEntity(scheduleDTO);
        scheduleEntity.setEmployeeEntity(employeeEntity);
        ScheduleEntity savedEntity = scheduleRepository.save(scheduleEntity);
        return ScheduleDTO.toDTO(savedEntity);
    }

    public List<ScheduleDTO> getAllEvents() {
        List<ScheduleEntity> entities = scheduleRepository.findAll();
        return entities.stream()
                .map(ScheduleDTO::toDTO)
                .collect(Collectors.toList());
    }

}
