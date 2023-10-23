package spring.project.groupware.academy.schedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.entity.ScheduleEntity;
import spring.project.groupware.academy.schedule.service.ScheduleService;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/full-calendar")
public class ScheduleRestController {

    private final ScheduleService scheduleService;


    // 스케줄 조회 (ALL)
//    @GetMapping
//    public ResponseEntity<List<ScheduleDTO>> getAllEvents() {
//        List<ScheduleDTO> events = scheduleService.getAllEvents();
//        return ResponseEntity.ok(events);
//    }

    // 스케줄 조회
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllEvents(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        EmployeeEntity currentUser = myUserDetails.getEmployeeEntity();
        List<ScheduleDTO> events = scheduleService.getAllEvents(currentUser);
        return ResponseEntity.ok(events);
    }

    // 스케줄 조회 end



    // 스케줄 추가
    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody ScheduleDTO scheduleDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        // 현재 로그인한 사용자의 EmployeeEntity 가져오기
        EmployeeEntity employeeEntity = myUserDetails.getEmployeeEntity(); // 현재 로그인한 사용자의 MemberEntity 가져오기

        if (employeeEntity == null) {
            log.info("사용자 정보가 없습니다.");
        }

        ScheduleDTO savedEvent = scheduleService.saveEvent(scheduleDTO, employeeEntity);
        if (savedEvent != null) {
            return ResponseEntity.ok(savedEvent);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the event");
        }
    }

    // 스케줄 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateEvent(@PathVariable Integer id, @RequestBody ScheduleDTO scheduleDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        // 현재 로그인한 사용자의 EmployeeEntity 가져오기
        EmployeeEntity employeeEntity = myUserDetails.getEmployeeEntity(); // 현재 로그인한 사용자의 MemberEntity 가져오기

        if (employeeEntity == null) {
            log.info("사용자 정보가 없습니다.");
        }

        ScheduleDTO updatedSchedule = scheduleService.updateEvent(id, scheduleDTO, employeeEntity);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }
    
    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer id) {
        try {
            scheduleService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully.");
        } catch (Exception e) {
            log.error("Failed to delete the event with id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the event");
        }
    }
















}
