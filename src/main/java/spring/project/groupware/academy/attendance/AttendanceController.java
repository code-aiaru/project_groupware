package spring.project.groupware.academy.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/attend")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    //한번에 처리하고 리스트 반환
    @PostMapping("/in")
    public void inAttend() {
//        if (classStartTime-?<LocalDateTime.now()<classStartTime+?){
//        }

        attendanceService.inAttend(LocalDateTime.now());

    }

    @PostMapping("/out")
    public void outAttend() {
//        if (classEndTime<LocalDateTime.now()){
//        }
        attendanceService.outAttend(LocalDateTime.now());

    }


    //
    @PostMapping("/sick")
    public void sickAttend() {
//        if (classEndTime<LocalDateTime.now()){
//        }
        attendanceService.sickAttend(LocalDateTime.now());

    }




}
