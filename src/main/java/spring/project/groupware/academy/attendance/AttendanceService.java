package spring.project.groupware.academy.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;
//    private final EmployeeRepository employeeRepository;
//    private final StudentRepository studentRepository;

    @Transactional
    public List<Attendance> CreateAttendance(){

////        List<Attendance> attendanceList = attendanceRepository.findAll();
//        for (Employee e : employeeRepository.findAll()){
//            Attendance.builder().attDate(LocalDate.now())
//                    .employee(e)
//                    .build();
//        }
//        return attendanceRepository.findAllDate(LocalDate.now());
        return null;
    }

    public void inAttend(Long employeeid) {

////        Attendance empAtt = employeeRepository.findById(employeeid).get().getAttendance();
//        Employee emp = employeeRepository.findById(employeeid).get();
//        Attendance empAtt = (Attendance) attendanceRepository.findByToday(LocalDate.now()).get();
//
//        if (LocalTime.now().isBefore(LocalTime.of(9,30,0))){
//            empAtt.builder().attendanceStatus(AttendanceStatus.IN).build();
//            empAtt.builder().inAtt(LocalDateTime.now()).build();
//        }else {
//            empAtt.builder().attendanceStatus(AttendanceStatus.LATE).build();
//            empAtt.builder().inAtt(LocalDateTime.now()).build();
//        }

    }

    public void outAttend(Long employeeid) {

//        Attendance empAtt = employeeRepository.findById(employeeid).get().getAttendance();
//        Employee emp = employeeRepository.findById(employeeid).get();
//        Attendance empAtt = (Attendance) attendanceRepository.findByToday(LocalDate.now()).get();
//
//        if (LocalTime.now().isAfter(LocalTime.of(17,50,0))){
//            empAtt.builder().attendanceStatus(AttendanceStatus.OUT).build();
//            empAtt.builder().inAtt(LocalDateTime.now()).build();
//        }else {
//            empAtt.builder().attendanceStatus(AttendanceStatus.OUTING).build();
//            empAtt.builder().inAtt(LocalDateTime.now()).build();
//        }

    }

    public List<AttendanceDto> attendanceList() {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        List<Attendance> attendanceList = attendanceRepository.findAll();

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }

        return attendanceDtoList;
    }

//    public static void sickAttend(LocalDateTime now) {
//    }
//    public static void vacationAttend(LocalDateTime now) {
//    }
//
//    public static void outingAttend(LocalDateTime now) {
//    }
//
//    public static void absentAttend(LocalDateTime now) {
//    }



}
