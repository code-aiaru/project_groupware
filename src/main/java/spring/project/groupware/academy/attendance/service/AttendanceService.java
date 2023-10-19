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
import java.time.*;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
//    private final StudentRepository studentRepository;


    // 기본 당일 모든 사원 출결목록 "결석"상태로 생성
    // 휴일, 토, 일, 공휴일 , 병가,휴가 제외  추가하기
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



    // 출결 조회

    //관리자가 수정,조회 가능하도록 시간,출결상태(휴가,병가) 예정(쿼리문 문제)
    //현재는 해당 사원 출결 조회
    public List<AttendanceDto> detailAttend(Long id) {
        //
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

        // 현재 사용중
        // 해당 사원 오늘 부터~ 이번달 마지막 일까지         // 변경 달 초 부터~ LocalDate.now().withDayOfMonth(1)
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndAttDateBetween(employee1, LocalDate.now().withDayOfMonth(1), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.MAX.getDayOfMonth()));






//        // 해당 사원 , 설정한 출근상태 기준으로, 정해진 기간1 ~ 기간2 까지
//        // msql 예시
//        //select * from attendance where employee_no='3' and attendance_status = 'OUT' and att_date between '2023-10-01' and '2023-10-10';
//        EmployeeEntity employee = employeeRepository.findById(3L)
//                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));
//        List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(employee, AttendanceStatus.OUT, LocalDate.of(2023,10,1), LocalDate.of(2023,10,10));

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


//        // 출결 상태에 따른 조회   ok
//        List<Attendance> attendanceList = attendanceRepository.findByAttendanceStatus(AttendanceStatus.VACATION);
        // 출결 상태, 기간 따른 조회  ok
//        List<Attendance> attendanceList = attendanceRepository.findByAttendanceStatusAndAttDateBetween(AttendanceStatus.ABSENT, LocalDate.now().minusDays(30), LocalDate.now());

        // 현재 사용중 예시
        // jpql     ok
        // 출결 상태 2가지 경우 중 하나 로 조회
        //@Query("SELECT a FROM Attendance a WHERE (a.attendanceStatus =:attStatus1 OR a.attendanceStatus =:attStatus2 ) AND a.attDate BETWEEN :startDate AND :endDate")
        List<Attendance> attendanceList = attendanceRepository.customAttDate(AttendanceStatus.LATE, AttendanceStatus.ABSENT, LocalDate.of(2023,9,25), LocalDate.of(2023,10,01));


        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }

        return attendanceDtoList;
    }

    public Page<AttendanceDto> attendancePagingList1(Pageable pageable, String subject, String set, String first, String last) {
        if(subject==null) subject="";   //subject="ALL"
        if(first==null||first=="") first=LocalDate.now().toString();
        if(last==null||last=="") last=LocalDate.now().toString();
        if(set==null||set=="") set="0";

        LocalDate start = null;
        LocalDate end = null;

        Page<Attendance> attendances =null;

        // 8자리or6자리 20231010 , 231010
        // 7, 30, 90,    달선택, 직접입력
        // 입사일부터 현재까지 조회 하도록 예정
        if(set.equals("0")||set.equals("100")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 적나, 안적나
            if (first.equals(LocalDate.now())&&last.equals(LocalDate.now())){   // 기간 미입력 >> 전체
                // 아래 출결 상태 조건문에 설정
            }else{                                                              // 기간 입력 >> 기간 설정
                set = "99";
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
            }

        }else if(set.equals("77")){   // 이번 주
            start = LocalDate.now().with(DayOfWeek.MONDAY);
            end = LocalDate.now().with(DayOfWeek.SUNDAY);
        }else if(set.equals("30")){     // 한달
            start = LocalDate.now().withDayOfMonth(1);
            end = YearMonth.from(LocalDate.now()).atEndOfMonth();
        }else if(set.equals("90")){     // 세달
            start = LocalDate.now().withDayOfMonth(1).minusMonths(3);
            end = YearMonth.from(LocalDate.now()).atEndOfMonth();
        }else if(0<Integer.parseInt(set) && Integer.parseInt(set)<13){  // 월 선택
            start = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), 1);
            end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.MAX.getDayOfMonth());
        }else if(set.equals("99")) {     // 직접 입력 (일단 예시)
            start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
            end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
//            System.out.println("end >> "+ end);
//            System.out.println("start >> "+ start);
        }else {

        }

        // set 조건 따라서 조회 조건 추가하기
        if (subject.equals("ALL")) {        // subject.equals(null) ||
            if (set.equals("0")) {
                attendances = attendanceRepository.findAll(pageable);
            }

            else{
                attendances = attendanceRepository.findByAttDateBetween(pageable, start ,end);
//                System.out.println("end >> "+ end);
//                System.out.println("start >> "+ start);
            }
        } else if (subject.equals("IN")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.IN);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.IN, start, end);
            }
        } else if (subject.equals("LATE")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.LATE);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.LATE, start, end);
            }
        }
        else if (subject.equals("OUT")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.OUT);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.OUT, start, end);
            }
        }else if (subject.equals("OUTING")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.OUTING);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.OUTING, start, end);
            }
        }else if (subject.equals("ABSENT")){
            if (set.equals("0")) {  // 결석(subject), 전체 기간(set)
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.ABSENT);
            }
            else {                  // 결석(subject), 직접 입력한 시작, 끝 기간
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.ABSENT, start, end);
            }
        }else if (subject.equals("SICK")){
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.SICK);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.SICK, start, end);
            }
        }else if (subject.equals("VACATION")){
            if (set.equals("0")) {
                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.VACATION);
            }
            else {
                attendances = attendanceRepository.findByAttendanceStatusAndAttDateBetween(pageable, AttendanceStatus.VACATION, start, end);
            }
        }else {
            attendances = attendanceRepository.findAll(pageable);
        }

        Page<AttendanceDto> attendanceDtoPageList = attendances.map(AttendanceDto::toAttendanceDto);
        return attendanceDtoPageList;
    }


    public Page<AttendanceDto> attendancePagingList2(Pageable pageable, Long id, String subject, String set, String first, String last) {
        // 사원 목록 >> 해당 사원 선택 >> id:해당 사원 아이디(employee_no) >> 변환 필요

//        EmployeeEntity employee = attendanceRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("정보가 없음!")).getEmployee();

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        if(subject==null) subject="";   //subject="ALL"
        if(first==null||first=="") first=LocalDate.now().toString();
        if(last==null||last=="") last=LocalDate.now().toString();
        if(set==null||set=="") set="0";

        LocalDate start = null;
        LocalDate end = null;

        Page<Attendance> attendances =null;

        // 8자리or6자리 20231010 , 231010
        // 7, 30, 90,    달선택, 직접입력
        // 입사일부터 현재까지 조회 하도록 예정
        if(set.equals("0")||set.equals("100")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 적나, 안적나
            if (first.equals(LocalDate.now())&&last.equals(LocalDate.now())){   // 기간 미입력 >> 전체
                // 아래 출결 상태 조건문에 설정
            }else{                                                              // 기간 입력 >> 기간 설정
                set = "99";
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
            }

        }else if(set.equals("77")){   // 이번 주
            start = LocalDate.now().with(DayOfWeek.MONDAY);
            end = LocalDate.now().with(DayOfWeek.SUNDAY);
        }else if(set.equals("30")){     // 한달
            start = LocalDate.now().withDayOfMonth(1);
            end = YearMonth.from(LocalDate.now()).atEndOfMonth();
        }else if(set.equals("90")){     // 세달
            start = LocalDate.now().withDayOfMonth(1).minusMonths(3);
            end = YearMonth.from(LocalDate.now()).atEndOfMonth();
        }else if(0<Integer.parseInt(set) && Integer.parseInt(set)<13){  // 월 선택
            start = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), 1);
            end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.MAX.getDayOfMonth());
        }else if(set.equals("99")) {     // 직접 입력 (일단 예시)
            start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
            end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
//            System.out.println("end >> "+ end);
//            System.out.println("start >> "+ start);
        }else {

        }

        // set 조건 따라서 조회 조건 추가하기
        if (subject.equals("ALL")) {        // subject.equals(null) ||
            if (set.equals("0")) {
                attendances = attendanceRepository.findAllByEmployee(pageable, employee);
            }

            else{
                attendances = attendanceRepository.findByEmployeeAndAttDateBetween(pageable, employee,  start ,end);
//                System.out.println("end >> "+ end);
//                System.out.println("start >> "+ start);
            }
        } else if (subject.equals("IN")) {
            if (set.equals("0")) {
//                attendances = attendanceRepository.findByAttendanceStatus(pageable, AttendanceStatus.IN);
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.IN);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.IN, start, end);
            }
        } else if (subject.equals("LATE")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.LATE);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.LATE, start, end);
            }
        }
        else if (subject.equals("OUT")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.OUT);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.OUT, start, end);
            }
        }else if (subject.equals("OUTING")) {
            if (set.equals("0")) {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.OUTING);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.OUTING, start, end);
            }
        }else if (subject.equals("ABSENT")){
            if (set.equals("0")) {  // 결석(subject), 전체 기간(set)
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.ABSENT);
            }
            else {                  // 결석(subject), 직접 입력한 시작, 끝 기간
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.ABSENT, start, end);
            }
        }else if (subject.equals("SICK")){
            if (set.equals("0")) {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.SICK);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.SICK, start, end);
            }
        }else if (subject.equals("VACATION")){
            if (set.equals("0")) {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatus(pageable, employee, AttendanceStatus.VACATION);
            }
            else {
                attendances = attendanceRepository.findByEmployeeAndAttendanceStatusAndAttDateBetween(pageable, employee, AttendanceStatus.VACATION, start, end);
            }
        }else {
            attendances = attendanceRepository.findAllByEmployee(pageable, employee);
        }

        Page<AttendanceDto> attendanceDtoPageList = attendances.map(AttendanceDto::toAttendanceDto);
        return attendanceDtoPageList;
    }
}
