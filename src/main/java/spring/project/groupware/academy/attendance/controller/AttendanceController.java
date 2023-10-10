package spring.project.groupware.academy.attendance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.attendance.service.AttendanceService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    //출퇴근
    @GetMapping("/in/{id}")
    public String inAttend(@PathVariable("id") Long id) {
        attendanceService.inAttend(id);
        return "redirect:/attendance/list";
    }

    @GetMapping("/out/{id}")
    public String outAttend(@PathVariable("id") Long id) {
        attendanceService.outAttend(id);
        return "redirect:/attendance/list";
    }

    //출결조회
    @GetMapping("/list")
    public String AttendList(Model model) {
        List<AttendanceDto> attendanceList = attendanceService.attendanceList();
        model.addAttribute("attendanceList", attendanceList);
        return "/attendanceList";
    }

    @GetMapping("/detail/{id}")
    public String detailAttend(@PathVariable("id") Long id,Model model) {
        List<AttendanceDto> attendanceList = attendanceService.detailAttend(id);
        model.addAttribute("attendanceList",attendanceList);
        return "/attendanceList";
    }



//    @PostMapping("/sick")
//    public void sickAttend() {
//        attendanceService.sickAttend(LocalDateTime.now());
//    }

//    @PostMapping("/vacation")
//    public void vacationAttend() {
//        attendanceService.vacationAttend(LocalDateTime.now());
//    }




}
