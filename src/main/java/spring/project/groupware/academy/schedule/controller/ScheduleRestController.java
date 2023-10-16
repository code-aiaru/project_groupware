package spring.project.groupware.academy.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.schedule.dto.ScheduleDTO;
import spring.project.groupware.academy.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/full-calendar")
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO eventDTO) {
        return scheduleService.createSchedule(eventDTO);
    }

    @PutMapping("/{id}")
    public ScheduleDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO eventDTO) {
        return scheduleService.updateSchedule(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }



}
