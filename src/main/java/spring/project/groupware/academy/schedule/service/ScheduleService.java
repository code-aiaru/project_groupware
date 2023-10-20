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

    // 이벤트 추가
    public ScheduleDTO saveEvent(ScheduleDTO scheduleDTO, EmployeeEntity employeeEntity) {
        ScheduleEntity scheduleEntity = ScheduleEntity.toEntity(scheduleDTO);
        scheduleEntity.setEmployeeEntity(employeeEntity);
        ScheduleEntity savedEntity = scheduleRepository.save(scheduleEntity);
        return ScheduleDTO.toDTO(savedEntity);
    }

    // 이벤트 조회 (ALL)
    public List<ScheduleDTO> getAllEvents() {
        List<ScheduleEntity> entities = scheduleRepository.findAll();
        return entities.stream()
                .map(ScheduleDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 이벤트 수정
    public ScheduleDTO updateEvent(Integer id, ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        scheduleEntity.updateFromDTO(scheduleDTO);
        ScheduleEntity updatedEntity = scheduleRepository.save(scheduleEntity);
        return ScheduleDTO.toDTO(updatedEntity);
    }
    
    // 이벤트 삭제
    public void deleteEvent(Integer id) {
        scheduleRepository.deleteById(id);
    }


}
