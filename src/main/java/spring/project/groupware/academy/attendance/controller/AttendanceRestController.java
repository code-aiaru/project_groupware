package spring.project.groupware.academy.attendance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.service.AttendanceService;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;
import spring.project.groupware.academy.student.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceRestController {

    private final AttendanceService attendanceService;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;


    @GetMapping("/create/now")
    public ResponseEntity<Integer> attendanceCreateNow(){
//        int attendanceToday = attendanceService.attendanceTodayCreate();
//        int attendance = attendanceService.attendanceCreateCustom1(LocalDate.now(), LocalDate.now());
        int attendance = attendanceService.attendanceTodayCreate();
        return new ResponseEntity<>(attendance,  HttpStatus.OK);
    }

     //대긴하는데 단순화 가능할거 같은데...
    @PostMapping("/create")
    public ResponseEntity<Integer> attendanceCreate(@RequestBody Map<String, String> requestMap) {

        String start = requestMap.get("start");
        String end = requestMap.get("end");

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        int attendance = attendanceService.attendanceCreateCustom1(startDate, endDate);

        if (attendance==0) {
            System.out.println("출결생성 문제발생");
        }else{
            System.out.println("출결생성 실행");
        }

        return new ResponseEntity<>(attendance,  HttpStatus.OK);
    }



    @GetMapping("/in")
    public ResponseEntity<Integer> inAttendance(
            @AuthenticationPrincipal MyUserDetails myUserDetails
//            @RequestBody AttendanceDto attendanceDto
//            @PathVariable("id")Long id
    ){
        // 사원, 학생 구분 처리


        if (myUserDetails != null) {
            Long employeeID = myUserDetails.getEmployeeEntity().getEmployeeNo();
            int rs = attendanceService.inAttend2(employeeID);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.OK);
        }
    }

    @GetMapping("/out")
    public ResponseEntity<Integer> outAttendance(
            @AuthenticationPrincipal MyUserDetails myUserDetails
//            @RequestBody AttendanceDto attendanceDto
//            @PathVariable("out")Long id
    ){
        // 사원, 학생 구분 처리
        if (myUserDetails != null) {
            Long employeeID = myUserDetails.getEmployeeEntity().getEmployeeNo();
            int rs = attendanceService.outAttend2(employeeID);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.OK);
        }
    }

    @PostMapping("/vacation2")
    public ResponseEntity<Integer> attendanceVacation2(@RequestBody Map<String, String> requestMap,
                                                       @AuthenticationPrincipal MyUserDetails myUserDetails
                                                       ) {

        Long employeeId = myUserDetails.getEmployeeEntity().getEmployeeNo();

        String start = requestMap.get("start");
        String end = requestMap.get("end");
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        int attendance = attendanceService.vacationApply(employeeId, startDate, endDate);

        if (attendance==0) {
            System.out.println("휴가신청 문제발생");
        }else{
            System.out.println("휴가신청 실행");
        }

        return new ResponseEntity<>(attendance,  HttpStatus.OK);
    }


    @PostMapping("/sick2")
    public ResponseEntity<Integer> attendanceSick2(@RequestBody Map<String, String> requestMap,
                                                   @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
//        myUserDetails.getEmployeeEntity();

        Long employeeId = myUserDetails.getEmployeeEntity().getEmployeeNo();

        String start = requestMap.get("start");
        String end = requestMap.get("end");
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        int attendance = attendanceService.sickApply(employeeId, startDate, endDate);

        if (attendance==0) {
            System.out.println("병가신청 문제발생");
        }else{
            System.out.println("병가신청 실행");
        }

        return new ResponseEntity<>(attendance,  HttpStatus.OK);
    }

    @GetMapping("/todayList")
    public ResponseEntity<List<AttendanceDto>> memberList(){
        List<AttendanceDto> todayList = attendanceService.todayList();
        return new ResponseEntity<>(todayList,HttpStatus.OK);
    }


}
