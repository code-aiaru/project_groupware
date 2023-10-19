package spring.project.groupware.academy.salary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;

    public List<SalaryDto> salaryList() {
        List<SalaryDto> salaryDtoList = new ArrayList<>();
        List<Salary> salaryList = salaryRepository.findAll();

        for (Salary salary : salaryList) {
            SalaryDto salaryDto = SalaryDto.toSalaryDto(salary);
            salaryDtoList.add(salaryDto);
        }

        return salaryDtoList;
    }

    public List<SalaryDto> detailSalary(Long id) {
        EmployeeEntity employeeEntity = salaryRepository.findById(id).get().getEmployee();

//        EmployeeEntity employee2 = employeeRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("사원정보가 없음!"));

        List<SalaryDto> salaryDtoList = new ArrayList<>();


        // 현재 사용중
        // 해당 사원 이번달 1일부터 ~ 이번달 마지막 일까지         // 변경 달 초 부터~ LocalDate.now().withDayOfMonth(1)
//        List<Salary> salaryList = salaryRepository.findByEmployeeAndSalaryDateBetween(employeeEntity, LocalDate.now().withDayOfMonth(1), LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.MAX.getDayOfMonth()));
        List<Salary> salaryList = salaryRepository.findByEmployeeAndSalaryDateBetween(employeeEntity, LocalDate.now().withDayOfMonth(1), YearMonth.from(LocalDate.now()).atEndOfMonth());

        for (Salary salary : salaryList) {
            SalaryDto salaryDto = SalaryDto.toSalaryDto(salary);
            salaryDtoList.add(salaryDto);
        }

        return salaryDtoList;
    }

    public Page<SalaryDto> salaryPagingList(Pageable pageable, Long id, String subject, String set, String first, String last) {

        Page<Salary> salarys = null;
        Page<SalaryDto> salaryDtoPageList = null;

        if (id == null) return null;
        if (subject == null)
            subject = "0";
//            subject="";
        if (first == null || first == "") first = LocalDate.now().toString();
        if (last == null || last == "") last = LocalDate.now().toString();
        if (set == null || set == "") set = "0";

        LocalDate start = null;
        LocalDate end = null;


        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("사원정보가 없음!"));

//        start =  LocalDate.of(Integer.valueOf(employee.getEmployeeBirth().substring(0,3)), Integer.valueOf(employee.getEmployeeBirth().substring(4,5)), Integer.valueOf(employee.getEmployeeBirth().substring(6,7)));
//        end = LocalDate.now();

//        if (subject.equals("0")){
//            salarys = salaryRepository.findByEmployee(pageable, employee);
//        }
//        // 8자리or6자리 20231010 , 231010
//        // 7, 30, 90,    달선택, 직접입력
//        // 입사일부터 현재까지 조회 하도록 예정
//        else
        if (first.equals(LocalDate.now().toString()) && last.equals(LocalDate.now().toString())) {// 기간 미입력 >> 전체
            // 기간 입력 >> 기간 설정
            set = "99";
//                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
//                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
//                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);

            if (set.equals("0")) {    // 기간옵션 설정(set) 전체 0 , 오늘 100, 직접 입력 99  >> 기간 적나, 안적나
                salarys = salaryRepository.findByEmployee(pageable, employee);
            } else if (set.equals("100")) {   // 상반기       // subject all,전부일때 문제

                if (!subject.equals("0")) {
                    start = LocalDate.of(Integer.parseInt(subject), 1, 1);
                    end = LocalDate.of(Integer.parseInt(subject), 6, LocalDate.MAX.getDayOfMonth());
                } else {
                    start = LocalDate.of(Integer.valueOf(employee.getEmployeeBirth().substring(0, 3)), Integer.valueOf(employee.getEmployeeBirth().substring(4, 5)), Integer.valueOf(employee.getEmployeeBirth().substring(6, 7)));
                    end = LocalDate.now();
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                    // 모든 년도 상반기로 추가?
                }
            } else if (set.equals("77")) {   // 하반기        // subject all,전부일때 문제
                if (!subject.equals("0")) {
//                start =  LocalDate.of(Integer.valueOf(employee.getEmployeeBirth().substring(0,3)), Integer.valueOf(employee.getEmployeeBirth().substring(4,5)), Integer.valueOf(employee.getEmployeeBirth().substring(6,7)));
//                end = LocalDate.now();
//                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start ,end);
//                // 모든 년도 상반기로 추가?
                } else {
                    start = LocalDate.of(Integer.parseInt(subject), 7, 1);
                    end = LocalDate.of(Integer.parseInt(subject), 12, LocalDate.MAX.getDayOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                }
            } else if (set.equals("30")) {     // 이번달      // subject all,전부일때 문제
                start = LocalDate.now().withDayOfMonth(1);
                end = YearMonth.from(LocalDate.now()).atEndOfMonth();
                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
            } else if (0 < Integer.parseInt(set) && Integer.parseInt(set) < 13) {  // 월 선택     // subject all,전부일때 문제
                if (!subject.equals("0")) {
//                start = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), 1);
//                end = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(set), LocalDate.MAX.getDayOfMonth());
//                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
//                // 모든 년도 정해진 달 추가
                } else {
                    start = LocalDate.of(Integer.parseInt(subject), Integer.parseInt(set), 1);
                    end = LocalDate.of(Integer.parseInt(subject), Integer.parseInt(set), LocalDate.MAX.getDayOfMonth());
                    salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
                }
            } else if (set.equals("99")) {     // 직접 입력 (일단 예시)
                start = LocalDate.of(Integer.parseInt(first.substring(0, 4)), Integer.parseInt(first.substring(5, 7)), Integer.parseInt(first.substring(8, 10)));
                end = LocalDate.of(Integer.parseInt(last.substring(0, 4)), Integer.parseInt(last.substring(5, 7)), Integer.parseInt(last.substring(8, 10)));
                salarys = salaryRepository.findByEmployeeAndSalaryDateBetween(pageable, employee, start, end);
            } else {

            }
        } else {
            salarys = salaryRepository.findByEmployee(pageable, employee);
        }


//        Page<SalaryDto> salaryDtoPageList = salarys.map(SalaryDto::toSalaryDto);
        salaryDtoPageList = salarys.map(SalaryDto::toSalaryDto);

        return salaryDtoPageList;
    }
}