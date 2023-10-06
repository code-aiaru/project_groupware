package spring.project.groupware.academy.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    @PostMapping("/in/{id}")
    public void inAttend(@PathVariable("id") Long id) {
////        if (classStartTime-?<LocalDateTime.now()<classStartTime+?){
////        }
//        int rs = attendanceRepository.findByDate(LocalDate.now());
//
//        if (rs==1){
//            attendanceService.inAttend(id);
//        }

    }

    @PostMapping("/out/{id}")
    public void outAttend(@PathVariable("id") Long id) {
//        if (classEndTime<LocalDateTime.now()){
//        }
//        attendanceService.outAttend(id);

    }

    @GetMapping("/list")
    public String detailAttend(Model model) {
        List<AttendanceDto> attendanceList = attendanceService.attendanceList();

        model.addAttribute("attendanceList", attendanceList);

        return "/attendance/list";
    }

//    @GetMapping("/detail/{id}")
//    public String detailAttend(@PathVariable("id") Long id) {
//        List<Attendance> attendanceList = attendanceService.detailAttendone(id);
//        return "/detailList";
//    }


//    //
//    @PostMapping("/sick")
//    public void sickAttend() {
////        if (classEndTime<LocalDateTime.now()){
////        }
//        attendanceService.sickAttend(LocalDateTime.now());
//
//    }
//
//    @PostMapping("/vacation")
//    public void vacationAttend() {
////        if (classEndTime<LocalDateTime.now()){
////        }
//        attendanceService.vacationAttend(LocalDateTime.now());
//
//    }




}
