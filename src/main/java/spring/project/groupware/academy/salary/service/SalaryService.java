package spring.project.groupware.academy.salary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;
import spring.project.groupware.academy.salary.dto.SalaryDto;
import spring.project.groupware.academy.salary.entity.Salary;
import spring.project.groupware.academy.salary.repository.SalaryRepository;
import spring.project.groupware.academy.student.repository.StudentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public List<SalaryDto> salaryList() {
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        List<Salary> salaryList = salaryRepository.findAll();

        for (Salary salary : salaryList) {
            SalaryDto salaryDto = SalaryDto.toSalaryDto(salary);
            salaryDtoList.add(salaryDto);
        }

        return salaryDtoList;
    }

    public SalaryDto salaryDetail(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("정보가 없음!"));

        SalaryDto salaryDto = SalaryDto.toSalaryDto(salary);

        return salaryDto;
    }

    public Page<SalaryDto> salaryPagingList(Pageable pageable, Long id, String subject, String set, String first, String last) {

        Page<Salary> salarys = null;

        if (id == null) return null;
        if (subject == null)
            subject = "0";
//            subject="";
        if (set == null || set == "") set = "0";
        if (first == null || first == "") first = LocalDate.now().toString();
        if (last == null || last == "") last = LocalDate.now().toString();

        LocalDate start = null;
        LocalDate end = null;

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("사원정보가 없음!"));


        LocalDate oldDate = salaryRepository.findOldestSalaryDateByEmployeeNo(id);

//        if (subject.equals("0")){
//            salarys = salaryRepository.findByEmployee(pageable, employee);
//        }
//        else
        if (first.equals(LocalDate.now().toString()) && last.equals(LocalDate.now().toString())) {// 기간 미입력 >> 전체
            // 기간 입력 >> 기간 설정       // 문제점
//            set = "99";
//                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
//                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
//                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
            salarys = salaryRepository.findByEmployee(pageable, employee);

            if (set.equals("0")) {    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 적나, 안적나
                if (!subject.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(subject), 1, 1);
                    end = LocalDate.of(Integer.parseInt(subject), 12, LocalDate.of(Integer.parseInt(subject),12,1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                }else{
                    salarys = salaryRepository.findByEmployee(pageable, employee);
                }
            } else if (set.equals("100")) {   // 상반기       // subject all,전부일때 문제
                if (!subject.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(subject), 1, 1);
                    end = LocalDate.of(Integer.parseInt(subject), 6, LocalDate.of(Integer.parseInt(subject),6,1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start ,end);
                } else {
                    start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
                    end = LocalDate.of(LocalDate.now().getYear(), 6, LocalDate.of(Integer.parseInt(subject),6,1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start ,end);

//                        for(int i=oldDate.getYear();<=LocalDate.now().getYear();i++) {
//                            start = LocalDate.of(oldDate.getYear(), oldDate.getMonth(), oldDate.getDayOfMonth());
//                            end = LocalDate.now();
//                            salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
//                        }
                    // 모든 년도 상반기로 추가?
                }
            } else if (set.equals("77")) {   // 하반기        // subject all,전부일때 문제
                if (!subject.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(subject), 7, 1);
                    end = LocalDate.of(Integer.parseInt(subject), 12, LocalDate.of(Integer.parseInt(subject),12,1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start ,end);
                    //                // 모든 년도 상반기로 추가?
                } else {
                    start = LocalDate.of(LocalDate.now().getYear(), 7, 1);
                    end = LocalDate.of(LocalDate.now().getYear(), 12, LocalDate.of(Integer.parseInt(subject),12,1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start ,end);

//                        for(int i=oldDate.getYear();<=LocalDate.now().getYear();i++) {
//                            start = LocalDate.of(oldDate.getYear(), oldDate.getMonth(), oldDate.getDayOfMonth());
//                            end = LocalDate.now();
//                            salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
//                        }
                }
            } else if (set.equals("30")) {     // 이번달      // subject all,전부일때 문제
                start = LocalDate.now().withDayOfMonth(1);
                end = YearMonth.from(LocalDate.now()).atEndOfMonth();
                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
            } else if (0 < Integer.parseInt(set) && Integer.parseInt(set) < 13) {  // 월 선택     // subject all,전부일때 문제
                if (!subject.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(subject), Integer.parseInt(set), 1);
                    end = LocalDate.of(Integer.parseInt(subject), Integer.parseInt(set), LocalDate.of(Integer.parseInt(subject),Integer.parseInt(set),1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                } else {
                    start = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), 1);
                    end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set),1).lengthOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                }
            } else if (set.equals("99")) {     // 직접 입력 (일단 예시)
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
            }
        } else {
            salarys = salaryRepository.findByEmployee(pageable, employee);
        }


//        Page<SalaryDto> salaryDtoPageList = salarys.map(SalaryDto::toSalaryDto);
        Page<SalaryDto> salaryDtoPageList = salarys.map(SalaryDto::toSalaryDto);

        return salaryDtoPageList;
    }



    //수정1
    public int salaryToday() {
        int hourPay = 9620;         // 2023 기준
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
//        int day = LocalDate.now().getDayOfMonth();

        // 변경일 신청시 다음달거 미리 생성 // 출결 처럼 만들기
        for (EmployeeEntity emp : employeeRepository.findAll()){
            //지급일 조회
            if (month==1){
                year = year-1;
                month = 12;
            }else {
                month = month-1;
            }
            // 지난달만 계산
            // 해당월 1~31까지 일

            // 저번달 급여 지급 있는지 확인
//            List<Salary> salaryList = salaryRepository.findByEmployeeAndSalaryDateBetween(emp, LocalDate.of(year,month,1), LocalDate.of(year,month, LocalDate.of(year,month,1).lengthOfMonth()));

            List<Attendance> attendancesList1 = attendanceRepository.findByEmployeeAndAttDateBetween(emp, LocalDate.of(year,month,1), LocalDate.of(year,month, LocalDate.of(year,month,1).lengthOfMonth()));
            if (attendancesList1.isEmpty()){
//                    continue;
            }else {
                int dayPay = 0;
                for (Attendance attendance : attendancesList1){
                    if (attendance==null) continue;
                    // 일한 시간 계산
                    LocalTime inAtt = attendance.getInAtt();
                    LocalTime outAtt = attendance.getOutAtt();

                    if (inAtt != null && outAtt != null) {
                        int workTime = (int) (ChronoUnit.MINUTES.between(outAtt, inAtt)/60);
                        dayPay = dayPay + (workTime * hourPay);
                    }else{
//                            continue;
                    }
                }
                salaryRepository.save(
                        Salary.builder()
                                .employee(emp)
                                .salaryDate(LocalDate.now())
                                .baseSalary(dayPay)
                                //  직급따라 추가 %?
                                // .incentiveSalary()
                                .build());
            }
        }
        return 1;
    }



}