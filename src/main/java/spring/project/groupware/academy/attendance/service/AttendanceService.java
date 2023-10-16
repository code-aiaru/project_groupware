package spring.project.groupware.academy.attendance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    //당일 모든 사원 출결목록 "결석"상태로 생성
    @Transactional
    public void CreateAttendance(){

        List<Attendance> vacationList = attendanceRepository.findByAttDateAndAttendanceStatus(LocalDate.now(), AttendanceStatus.VACATION);
        List<Attendance> sickList = attendanceRepository.findByAttDateAndAttendanceStatus(LocalDate.now(), AttendanceStatus.SICK);
//        List<Attendance> attList = attendanceRepository.findByAttDateAndAttendanceStatusOrAttendanceStatus(LocalDate.now(), AttendanceStatus.SICK, AttendanceStatus.VACATION);

        for (EmployeeEntity emp : employeeRepository.findAll()){
//            Attendance vacation = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.VACATION, e,LocalDate.now());
//            Attendance sick = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.SICK, e,LocalDate.now());
//            for (Attendance a : vacationList){
//                if(a.getEmployee().equals(emp)){
//                    attendanceRepository.save(
//                            Attendance.builder()
//                                    .inAtt(a.getInAtt())
//                                    .outAtt(a.getOutAtt())
//                                    .attDate(LocalDate.now())
//                                    .employee(emp)
//                                    .attendanceStatus(AttendanceStatus.VACATION)
//                                    .build());
//                }
//            }
//            for (Attendance a : sickList){
//                if(a.getEmployee().equals(emp)){
//                    attendanceRepository.save(
//                            Attendance.builder()
//                                    .inAtt(a.getInAtt())
//                                    .outAtt(a.getOutAtt())
//                                    .attDate(LocalDate.now())
//                                    .employee(emp)
//                                    .attendanceStatus(AttendanceStatus.SICK)
//                                    .build());
//                }
//            }

            attendanceRepository.save(
                    Attendance.builder()
                            .attDate(LocalDate.now())
                            .employee(emp)
                            .attendanceStatus(AttendanceStatus.ABSENT)
                            .build());


        }
//        return attendanceRepository.findAllDate(LocalDate.now());
    }

    //출석
    public void inAttend(Long attId) {
        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));

        if (LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(),LocalTime.of(9,30,0)))){

            attendance.changeInTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.IN);
        }else {
            attendance.changeInTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.LATE);

        }

//            return attendanceRepository.save(attendance);
        attendanceRepository.save(attendance);


    }

    //퇴근
    public void outAttend(Long attId) {

        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));

        if (LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(),LocalTime.of(17,50,0)))){

            attendance.changeOutTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.OUT);
        }else {
            attendance.changeOutTime(LocalDateTime.now());
            attendance.changeAttStatus(AttendanceStatus.OUTING);

        }

        attendanceRepository.save(attendance);
    }

    public void sickApply(Long empId, LocalDateTime start, LocalDateTime end) {
        EmployeeEntity employee = employeeRepository.findById(empId)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        Attendance attendance = attendanceRepository.findByAttDateAndEmployee(LocalDate.now(),employee);

//        Attendance attendance = attendanceRepository.findByAttDate(attId)
//                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));


        attendanceRepository.save(
                attendance.builder()
                        .employee(employee)
                        .attDate(LocalDate.now())   // 신청일
                        .inAtt(start)               // 시작일
                        .outAtt(end)                // 종료일
                        .attendanceStatus(AttendanceStatus.SICK)
                        .build());
    }

    public void vacationApply(Long empId, LocalDateTime start, LocalDateTime end) {
        EmployeeEntity employee = employeeRepository.findById(empId)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));


        Attendance attendanceList = attendanceRepository.findByAttDateAndEmployee(LocalDate.now(),employee);

        attendanceRepository.save(
                Attendance.builder()
                        .employee(employee)
                        .attDate(LocalDate.now())   // 신청일
                        .inAtt(start)               // 시작일
                        .outAtt(end)                // 종료일
                        .attendanceStatus(AttendanceStatus.VACATION)
                        .build());
    }



    // 출결 조회 ㅠㅠ

    //관리자가 수정,조회 가능하도록 시간,출결상태(휴가,병가) 예정(쿼리문 문제)
    //현재는 해당 사원 출결 조회
    public List<AttendanceDto> detailAttend(Long id) {

        //
        EmployeeEntity employee1 = attendanceRepository.findById(id).get().getEmployee();

//        EmployeeEntity employee2 = employeeRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        // 해당 사원 오늘 출결 조회
//        List<Attendance> attendanceList = attendanceRepository.findByEmployee(employee);

//        // 해당 사원  당월 모든 일수 출결 조회
//        LocalDate(LocalDate.now().getYear(), LocalDate.now().getMonth() , LocalDate.MAX.getDayOfMonth())
//        List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndAttDateBetween(employee, LocalDate.now(), LocalDate(LocalDate.now().getYear() , LocalDate.now().getMonth() , LocalDate.MAX.getDayOfMonth()));

        //
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndAttDateBetween(employee1, LocalDate.now(), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.MAX.getDayOfMonth()));



//        List<Attendance> attendanceList = attendanceRepository.employeeAtt(id);

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }

        return attendanceDtoList;
    }



    public List<AttendanceDto> attendanceList() {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
//        List<Attendance> attendanceList = attendanceRepository.findAll();
//

//        List<Attendance> attendanceList = attendanceRepository.customAttDate(LocalDate.of(2023,9,10), LocalDate.of(2023,10,20));
//        List<Attendance> attendanceList = attendanceRepository.customAttDate("ABSENT", "LATE", LocalDate.of(2023,9,10), LocalDate.of(2023,10,20));
        List<Attendance> attendanceList = attendanceRepository.customAttDate(AttendanceStatus.LATE, AttendanceStatus.ABSENT, LocalDate.of(2023,9,25), LocalDate.of(2023,10,01));

//        // 출결 상태에 따른 조회   ok
//        List<Attendance> attendanceList = attendanceRepository.findByAttendanceStatus(AttendanceStatus.VACATION);
        // 출결 상태, 기간 따른 조회  ok
//        List<Attendance> attendanceList = attendanceRepository.findByAttendanceStatusAndAttDateBetween(AttendanceStatus.ABSENT, LocalDate.now().minusDays(30), LocalDate.now());

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }

        return attendanceDtoList;
    }

    public Page<AttendanceDto> attendancePagingList(Pageable pageable, String subject, String term) {
        if(subject==null) subject="";
        Page<Attendance> attendances =null;

//        if (term.equals(null) || term.equals("0") || term.equals("")) {
//            LocalDate date = LocalDate.now();
//        } else {
//            LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
//        }

        if (subject.equals("ALL")) {        // subject.equals(null) ||
            if(term==null) term="";
            if (term.equals("0") || term.equals("")) {
                attendances = attendanceRepository.findAll(pageable);
            }
            else{
                LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
//                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.IN, LocalDate.now().minusDays(Integer.valueOf(term)) ,LocalDate.now());
                attendances = attendanceRepository.findByAttDateBetween(pageable, date ,LocalDate.now());
            }
        } else if (subject.equals("IN")) {
            if (term.equals("0") || term.equals("")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.IN);
            }else {
                LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.IN, date, LocalDate.now());
            }
        } else if (subject.equals("LATE")) {
            if (term.equals("0") || term.equals("")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.LATE);
            }else {
                LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.LATE, date , LocalDate.now());
            }
        }else if (subject.equals("OUT")) {
            if (term.equals("0") || term.equals("")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.OUT);
            }else {
                LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.OUT, date, LocalDate.now());
            }
        } else if (subject.equals("ABSENT")){
            if (term.equals("0") || term.equals("")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.ABSENT);
            }else {
                LocalDate date = LocalDate.now().minusDays(Integer.valueOf(term));
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.ABSENT, date, LocalDate.now());
            }
        } else {
            attendances = attendanceRepository.findAll(pageable);
        }

        Page<AttendanceDto> attendanceDtoPageList = attendances.map(AttendanceDto::toAttendanceDto);
        return attendanceDtoPageList;
    }


}
