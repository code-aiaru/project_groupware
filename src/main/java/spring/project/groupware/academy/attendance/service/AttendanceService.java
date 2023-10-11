package spring.project.groupware.academy.attendance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
//    private final StudentRepository studentRepository;


//    public void inAttend(Long employee_no) {
//        EmployeeEntity emp = employeeRepository.findById(employee_no).get();
//        List<Attendance> empAtt = attendanceRepository.findByEmployeeAndAttDate(employee_no, LocalDate.now());
//        List<Attendance> empAtt = attendanceRepository.findByAttdate(employee_no, LocalDate.now());
//        List<Attendance> empAtt = attendanceRepository.findByEmployeeAndAttDate(emp, LocalDate.now());
//        if (LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,30,0)))){
//            empAtt.get(0).changeInTime(LocalDateTime.now());
//            empAtt.get(0).changeAttStatus(AttendanceStatus.IN);
//        }else {
//            empAtt.get(0).changeOutTime(LocalDateTime.now());
//            empAtt.get(0).changeAttStatus(AttendanceStatus.LATE);
//        }
//    }

    //당일 모든 사원 출결목록 "결석"상태로 생성
    @Transactional
    public void CreateAttendance(){
//        List<Attendance> attendanceList = attendanceRepository.findAll();
        for (EmployeeEntity e : employeeRepository.findAll()){
            attendanceRepository.save(
                    Attendance.builder()
                            .attDate(LocalDate.now())
                            .employee(e)
                            .attendanceStatus(AttendanceStatus.ABSENT)
                            .build());
        }
//        return attendanceRepository.findAllDate(LocalDate.now());
    }

    //출석
    public void inAttend(Long attId) {
        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 X"));

        if (LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,30,0)))){

            attendance.changeInTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.IN);
        }else {
            attendance.changeInTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.LATE);
        }
        attendanceRepository.save(attendance);
    }

    //퇴근
    public void outAttend(Long attId) {
        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 X"));

        if (LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(),LocalTime.of(17,50,0)))){

            attendance.changeOutTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.OUT);
        }else {
            attendance.changeOutTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.OUTING);
        }
        attendanceRepository.save(attendance);
    }

    //날짜,회원,출결상태 따라 조회하기 (쿼리문 문제)
    public List<AttendanceDto> attendanceList() {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        List<Attendance> attendanceList = attendanceRepository.findAll();

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }

    //관리자가 수정,조회 가능하도록 시간,출결상태(휴가,병가) 예정(쿼리문 문제)
    public List<AttendanceDto> detailAttend(Long id) {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        List<Attendance> attendanceList = attendanceRepository.findAll();
//        List<Attendance> attendanceList = employeeRepository.findByAttends(id);

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }



}
