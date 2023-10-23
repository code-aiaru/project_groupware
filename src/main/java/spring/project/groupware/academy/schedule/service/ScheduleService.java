package spring.project.groupware.academy.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.employee.constraint.Role;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;
import spring.project.groupware.academy.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

//    // 이벤트 조회 (ALL)
//    public List<ScheduleDTO> getAllEvents() {
//        List<ScheduleEntity> entities = scheduleRepository.findAll();
//        return entities.stream()
//                .map(ScheduleDTO::toDTO)
//                .collect(Collectors.toList());
//    }

    // 조회 로직

    public List<ScheduleDTO> getAllEvents(EmployeeEntity currentUser) {
        List<ScheduleEntity> entities = scheduleRepository.findAll();
        log.info("Total schedules found: {}", entities.size());

        List<ScheduleDTO> result = entities.stream()
                .filter(entity -> {
                    boolean allowed = isAllowedToView(entity, currentUser);
                    if(!allowed) {
                        log.info("Filtered out schedule with id: {} for target: {}", entity.getId(), entity.getTarget());
                    }
                    return allowed;
                })
                .map(ScheduleDTO::toDTO)
                .collect(Collectors.toList());

        log.info("Total schedules after filtering: {}", result.size());

        return result;
    }

    private boolean isAllowedToView(ScheduleEntity entity, EmployeeEntity currentUser) {
        String target = entity.getTarget();
        Role role = currentUser.getRole();
        log.info("current user's role: {}", role);
        Long ownerId = entity.getEmployeeEntity().getEmployeeNo();
        Long currentUserId = currentUser.getEmployeeNo();

        switch (target) {
            case "":
                return true; // 자바스크립트에서 공백은 걸러내도록 로직을 짜놓았으나 혹시 몰라서 해놓음.
            case "ALL":
                return true;
            case "EMPLOYEE":
                return role == Role.EMPLOYEE || role == Role.ADMIN;
            case "ADMIN":
                return role == Role.ADMIN;
            case "PERSONAL":
                return currentUserId.equals(ownerId);  // 일정 작성자만 볼 수 있음
            default:
                return false;
        }
    }



    // 조회 로직 end









    // 이벤트 수정
    public ScheduleDTO updateEvent(Integer id, ScheduleDTO scheduleDTO, EmployeeEntity currentUser) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        Role role = currentUser.getRole();

        // 사용자가 'ADMIN'이 아니고, 동시에 글 작성자도 아니라면 예외 발생
        if (!role.name().equals("ADMIN") && !currentUser.equals(scheduleEntity.getEmployeeEntity())) {
            throw new RuntimeException("글 작성자가 아니거나 ADMIN 권한이 없습니다.");
        }

        scheduleEntity.updateFromDTO(scheduleDTO);
        ScheduleEntity updatedEntity = scheduleRepository.save(scheduleEntity);
        return ScheduleDTO.toDTO(updatedEntity);
    }


    // 이벤트 삭제
    public void deleteEvent(Integer id) {
        scheduleRepository.deleteById(id);
    }


}
