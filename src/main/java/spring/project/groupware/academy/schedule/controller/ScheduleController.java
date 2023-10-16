package spring.project.groupware.academy.schedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScheduleController {

    @GetMapping({"/schedule"})
    public String getSchedulePage() {
        log.info("schedule method activated");
        return "schedule/schedule";
    }


}
