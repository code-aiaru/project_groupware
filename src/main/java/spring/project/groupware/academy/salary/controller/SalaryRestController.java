package spring.project.groupware.academy.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;
import spring.project.groupware.academy.salary.repository.SalaryRepository;
import spring.project.groupware.academy.salary.service.SalaryService;
import spring.project.groupware.academy.student.repository.StudentRepository;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryRestController {

    private final SalaryService salaryService;
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;

//    // 급여 계산
//    @GetMapping("/calculator")
//    public ResponseEntity<Integer> salaryCalculator(){
//        // 일단 지난달 1~31 계산해서 신청일에 지급!
//        // 수정한다면 지급일 변경된다면 전월 기존일 ~ 이번월 변경일
//        int rs = salaryService.salaryToday();
//
//        return new ResponseEntity<>(rs,  HttpStatus.OK);
//    }

}
