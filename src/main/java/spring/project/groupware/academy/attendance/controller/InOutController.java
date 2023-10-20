//package spring.project.groupware.academy.attendance.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import spring.project.groupware.academy.attendance.dto.AttendanceDto;
//import spring.project.groupware.academy.attendance.service.AttendanceService;
//
//@RestController
//@RequestMapping("/api/attendance")
//@RequiredArgsConstructor
//public class InOutController {
//
//    private final AttendanceService attendanceService;
//
//    @PostMapping("/in")
//    public ResponseEntity<Integer> inAttendance(@RequestBody AttendanceDto attendanceDto){
//        int rs = attendanceService.inAttend(attendanceDto);
//        return new ResponseEntity<>(rs, HttpStatus.OK);
//    }
//
//}
