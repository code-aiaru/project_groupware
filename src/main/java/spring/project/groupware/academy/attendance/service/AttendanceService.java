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
import spring.project.groupware.academy.student.entity.StudentEntity;
import spring.project.groupware.academy.student.repository.StudentRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;

    // 기본 당일 모든 사원 출결목록 "결석"상태로 생성
    // 휴일, 토, 일, 공휴일 , 병가,휴가 제외  추가하기
    @Transactional
    public int attendanceTodayCreate(){

        for (EmployeeEntity emp : employeeRepository.findAll()) {
            // 수정 가능성 있음
//            LocalDate vacationDate = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.VACATION, emp,LocalDate.now());
//            LocalDate sickDate = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.SICK, emp,LocalDate.now());
//            LocalDate vacationDate = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.VACATION, emp,LocalDate.now()).orElseThrow(() -> new EntityNotFoundException("근태관리 테이블에 해당하는 데이터가 없음")).getAttDate();
//            LocalDate sickDate = attendanceRepository.findByAttendanceStatusAndEmployeeAndAttDate(AttendanceStatus.SICK, emp,LocalDate.now()).orElseThrow(() -> new EntityNotFoundException("근태관리 테이블에 해당하는 데이터가 없음")).getAttDate();

            // 이미 존재하는 출결 사원 건너뜀(병가,휴가 등?)
            if (attendanceRepository.existsByAttDateAndEmployee(LocalDate.now(), emp)) continue;

            if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                System.out.println("토요일, 일요일은 휴일 입니다.");
                // 휴일 입니다 팝업 표시 // 공휴일..
            } else {
                attendanceRepository.save(
                        Attendance.builder()
                                .attDate(LocalDate.now())
                                .employee(emp)
                                .attendanceStatus(AttendanceStatus.ABSENT)
                                .build());

            }
        }

        for (StudentEntity student : studentRepository.findAll()) {
            // 이미 존재하는 출결 사원 건너뜀(병가,휴가 등?)
            if (attendanceRepository.existsByAttDateAndStudent(LocalDate.now(), student)) continue;

            if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                System.out.println("토요일, 일요일은 휴일 입니다.");
                // 휴일 입니다 팝업 표시 // 공휴일..
                return 0;
            } else {
                attendanceRepository.save(
                        Attendance.builder()
                                .attDate(LocalDate.now())
                                .student(student)
                                .attendanceStatus(AttendanceStatus.ABSENT)
                                .build());

            }
        }

        for (EmployeeEntity emp : employeeRepository.findAll()) {
            // 이미 존재하는 출결 사원 건너뜀(병가,휴가 등?)
            if (attendanceRepository.existsByAttDateAndEmployee(LocalDate.now(), emp)) continue;

            if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                System.out.println("토요일, 일요일은 휴일 입니다.");
                // 휴일, 공휴일
                return 0;
            } else {
                attendanceRepository.save(
                        Attendance.builder()
                                .attDate(LocalDate.now())
                                .employee(emp)
                                .attendanceStatus(AttendanceStatus.ABSENT)
                                .build());

            }
        }

        return 1;
    }


    @Transactional
    public int attendanceCreateCustom1(LocalDate start, LocalDate end){
        if (start == null || end == null) {
            return 0;
        }

        int day = end.getDayOfYear() - start.getDayOfYear();
        if (day==0) day=1;      // 당일 생성

        for (EmployeeEntity emp : employeeRepository.findAll()) {
            for (int i = 0; i <=day; i++) {
                LocalDate date = start.plusDays(i); // 반복문 일자
                // 이미 존재하는 출결 사원 건너뜀(병가,휴가 등)
                if (attendanceRepository.existsByAttDateAndEmployee(date,emp)) continue;
                // 해당일자에 해당하는 병가, 휴가 확인 >> countBy, existsBy
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                } else {

                    attendanceRepository.save(
                            Attendance.builder()
                                    .attDate(date)
                                    .employee(emp)
                                    .attendanceStatus(AttendanceStatus.ABSENT)
                                    .build());
                }
            }
        }

        for (StudentEntity student : studentRepository.findAll()) {
            for (int i = 0; i <=day; i++) {
                LocalDate date = start.plusDays(i); // 반복문 일자
                // 이미 존재하는 출결 사원 건너뜀(병가,휴가 등)
                if (attendanceRepository.existsByAttDateAndStudent(date,student)) continue;
                // 해당일자에 해당하는 병가, 휴가 확인 >> countBy, existsBy
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                } else {
                    attendanceRepository.save(
                            Attendance.builder()
                                    .attDate(date)
                                    .student(student)
                                    .attendanceStatus(AttendanceStatus.ABSENT)
                                    .build());
                }
            }
        }
        return  1;
    }

    //출석
    public void inAttend1(Long attId) {
        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));

        if (attendance != null) {
            if (LocalTime.now().isBefore(LocalTime.of(9, 30, 0))) {
                attendance.changeInTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.IN);
            } else {
                attendance.changeInTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.LATE);
            }
            attendanceRepository.save(attendance);

        } else {

        }
    }

    //퇴근
    public void outAttend1(Long attId) {

        Attendance attendance = attendanceRepository.findById(attId)
                .orElseThrow(() -> new EntityNotFoundException("근태관리 테이블 id에 해당하는 데이터가 없음"));

        if (attendance != null) {
            if (attendance.getAttendanceStatus().equals(AttendanceStatus.IN) || attendance.getAttendanceStatus().equals(AttendanceStatus.LATE)) {
                if (LocalTime.now().isBefore(LocalTime.of(17, 50, 0))) {
                    attendance.changeOutTime(LocalTime.now());
                    attendance.changeAttStatus(AttendanceStatus.OUTING);
                } else {
                    attendance.changeOutTime(LocalTime.now());
                    attendance.changeAttStatus(AttendanceStatus.OUT);
                }

                attendanceRepository.save(attendance);
            }

//            return 1;
        } else {
//            return 0;
        }
    }



    //출석    //사원용
    public int inAttend2(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId).get();
        Attendance attendance = attendanceRepository.findByAttDateAndEmployee(LocalDate.now(), employee);

        if (attendance != null) {
            if (LocalTime.now().isBefore(LocalTime.of(9, 30, 0))) {
                attendance.changeInTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.IN);
            } else {
                attendance.changeInTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.LATE);
            }

            attendanceRepository.save(attendance);

            return 1;
        } else {
            return 0;
        }
    }


    //퇴근    // 사원용
    public int outAttend2(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId).get();
        Attendance attendance = attendanceRepository.findByAttDateAndEmployee(LocalDate.now(), employee);

        // 출근이 찍힌 경우
        if (attendance.getAttendanceStatus().equals(AttendanceStatus.IN) || attendance.getAttendanceStatus().equals(AttendanceStatus.LATE)) {
            if (LocalTime.now().isBefore(LocalTime.of(17,50,0))){

                attendance.changeOutTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.OUTING);
            } else {
                attendance.changeOutTime(LocalTime.now());
                attendance.changeAttStatus(AttendanceStatus.OUT);

            }
            attendanceRepository.save(attendance);
            return 1;
        }else {
            return 0;
        }
    }

    public int sickApply(Long empId, LocalDate start, LocalDate end) {
        EmployeeEntity employee = employeeRepository.findById(empId)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        int day = end.getDayOfYear()-start.getDayOfYear();

        for (int i=0;i<=day;i++) {
            LocalDate date = start.plusDays(i);
            if (date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY){
                continue;
            }else {
//                        if (emp.getCreateTime().toLocalDate().isBefore(date))     // empDate(사원생성일) < date(비교일) 사원 생성일 기준보다 작으면 넘어감
//                        {}else {
                attendanceRepository.save(
                        Attendance.builder()
                                .attDate(date)
                                .employee(employee)
                                .start(start)          // 시작날 추가
                                .end(end)         // 끝날 추가
                                .applyDate(LocalDate.now()) //신청일
                                .attendanceStatus(AttendanceStatus.SICK)
                                .build());
//                        }


            }
        }
//        }

        // 임시
        return 1;
    }

    public int vacationApply(Long empId, LocalDate start, LocalDate end) {
        EmployeeEntity employee = employeeRepository.findById(empId)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        int day = end.getDayOfYear()-start.getDayOfYear();

        for (int i=0;i<=day;i++) {
            LocalDate date = start.plusDays(i);
            if (date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY){
                continue;
            }else {
                attendanceRepository.save(
                        Attendance.builder()
                                .attDate(date)
                                .employee(employee)
                                .start(start)          // 시작날 추가
                                .end(end)         // 끝날 추가
                                .applyDate(LocalDate.now()) // 신청일
                                .attendanceStatus(AttendanceStatus.VACATION)
                                .build());
            }
        }
        return 1;
    }

    public int attendanceUpdate(AttendanceDto attendanceDto) {
        Optional<Attendance>  optionalAttendance=
                Optional.ofNullable(attendanceRepository.findById(attendanceDto.getId()).orElseThrow(() -> {
                    return new IllegalArgumentException("값이 없습니다.");
                }));
        Attendance attendance=
                Attendance.builder()
                        .id(attendanceDto.getId())
                        .employee(attendanceDto.getEmployee())
//                        .student()
                        .attDate(attendanceDto.getAttDate())
                        .inAtt(attendanceDto.getInAtt())
                        .outAtt(attendanceDto.getOutAtt())
                        .attendanceStatus(attendanceDto.getAttendanceStatus())
//                        .applyDate()
//                        .start()
//                        .end()
                        .build();

        Long Id= attendanceRepository.save(attendance).getId();

        Optional<Attendance>  optionalAttendance2=
                Optional.ofNullable(attendanceRepository.findById(Id).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 값이 없습니다.");
                }));

        if(optionalAttendance2.isPresent()){
            return 1;
        }
        return 0;
    }

    @Transactional
    public AttendanceDto attendanceUpdateOk(Long id) {
        Optional<Attendance> optionalAttendance =
                Optional.ofNullable(attendanceRepository.findById(id).orElseThrow(()->{
                    return new IllegalArgumentException("수정 값이 없습니다.");
                }));

        Attendance attendance = optionalAttendance.get();

        if (optionalAttendance.isPresent()){
            AttendanceDto attendanceDto = AttendanceDto.builder()
                    .id(attendance.getId())
                    .employee(attendance.getEmployee())
//                        .student()
                    .attDate(attendance.getAttDate())
                    .inAtt(attendance.getInAtt())
                    .outAtt(attendance.getOutAtt())
                    .attendanceStatus(attendance.getAttendanceStatus())
//                        .applyDate()
//                        .start()
//                        .end()
                    .build();
            return attendanceDto;
        }

        return null;
    }


    // 출결 조회
    //관리자가 수정,조회 가능하도록 시간,출결상태(휴가,병가)
    //현재는 해당 사원 출결 조회
    public List<AttendanceDto> detailAttend(Long id) {
        EmployeeEntity employee1 = attendanceRepository.findById(id).get().getEmployee();

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();

        // 현재 사용중
        // 해당 사원 오늘 부터 ~ 이번달 마지막 일까지 // 달 1일 부터 ~ 달 끝날 LocalDate.now().withDayOfMonth(1)
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndAttDateBetween(employee1, LocalDate.now().withDayOfMonth(1), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),1).lengthOfMonth()));

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }

        return attendanceDtoList;
    }



    public List<AttendanceDto> attendanceList() {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        // 이번달 조회
        List<Attendance> attendanceList = attendanceRepository.findByAttDateBetween(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().lengthOfMonth()));

        for (Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = AttendanceDto.toAttendanceDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }

    public Page<AttendanceDto> attendancePagingList1(Pageable pageable, String subject, String set, String first, String last) {
        if(subject==null)
//            subject="";
            subject="ALL";
        if(first==null||first=="") first="0";
        if(last==null||last=="") last="0";
        if(set==null||set=="") set="0";

        LocalDate start = null;
        LocalDate end = null;

        Page<Attendance> attendances =null;

        if(set.equals("0")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 유무
            if (first.equals("0")||last.equals("0")){   // 기간 미입력 >> 전체
                // 아래 출결 상태 조건문
                if (first.equals("0")) start=LocalDate.now();   // 입력 없을시 우선 당일
                if (last.equals("0")) end=LocalDate.now();   // 입력 없을시 우선 당일
            }else{                                                              // 기간 입력 >> 기간 설정
                set = "99";
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
            }
        }else if(set.equals("100")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 유무
            start = LocalDate.now();
            end = LocalDate.now();
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
            end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(), 1).lengthOfMonth());
        }else if(set.equals("99")) {     // 직접 입력 (일단 예시)
            start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
            end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
        }else{
//            attendances = attendanceRepository.findAllByEmployee(pageable, employee);
//            Page<AttendanceDto> attendanceDtoPageList = attendances.map(AttendanceDto::toAttendanceDto);
//            return attendanceDtoPageList;
        }

        // set 조건 따라서 조회 조건 추가
        if (subject.equals("ALL")) {        // subject.equals(null) ||
            if (set.equals("0")) {
                attendances = attendanceRepository.findAll(pageable);
            }

            else{
                attendances = attendanceRepository.findByAttDateBetween(pageable, start ,end);
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

        // 사원 목록 >> 해당 사원 선택 >> id:해당 사원 아이디(employee_no) >> 변환
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        if(subject==null)
//            subject="";
            subject="ALL";
        if(first==null||first=="") first="0";
        if(last==null||last=="") last="0";
        if(set==null||set=="") set="0";

        LocalDate start = null;
        LocalDate end = null;

        Page<Attendance> attendances =null;

        if(set.equals("0")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 유무
            if (first.equals("0")||last.equals("0")){   // 기간 미입력 >> 전체
                // 아래 출결 상태 조건문
                if (first.equals("0")) start=LocalDate.now();   // 입력 없을시 우선 당일
                if (last.equals("0")) end=LocalDate.now();   // 입력 없을시 우선 당일
            }else{                                                              // 기간 입력 >> 기간 설정
                set = "99";
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
            }
        }else if(set.equals("100")){    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 유무
            start = LocalDate.now();
            end = LocalDate.now();
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
            end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(), 1).lengthOfMonth());
        }else if(set.equals("99")) {     // 직접 입력 (일단 예시)
            start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
            end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
        }else{
//            attendances = attendanceRepository.findAllByEmployee(pageable, employee);
//            Page<AttendanceDto> attendanceDtoPageList = attendances.map(AttendanceDto::toAttendanceDto);
//            return attendanceDtoPageList;
        }

        // set 조건 따라서 조회 조건 추가
        if (subject.equals("ALL")) {        // subject.equals(null) ||
            if (set.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                    end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
                    attendances = attendanceRepository.findByEmployeeAndAttDateBetween(pageable, employee,  start ,end);
            }
            else{
                attendances = attendanceRepository.findByEmployeeAndAttDateBetween(pageable, employee,  start ,end);
            }
        } else if (subject.equals("IN")) {
            if (set.equals("0")) {
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



    public List<AttendanceDto> todayList() {
        List<AttendanceDto> todayList = new ArrayList<>();
        //

        return todayList;
    }



}


