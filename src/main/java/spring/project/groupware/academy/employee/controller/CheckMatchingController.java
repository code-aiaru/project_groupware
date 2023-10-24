package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;

@RestController
@RequiredArgsConstructor
public class CheckMatchingController {

    private final EmployeeService employeeService;

    // 비밀번호 찾기 기능에서 이메일과 휴대전화번호 둘다 일치하는지
    @GetMapping("/api/check-emailPhoneMatching")
    public EmployeeDto checkEmailPhoneMatching(@RequestParam String employeeEmail, @RequestParam String employeePhone) {

        boolean isMatching = employeeService.checkEmailPhoneMatching(employeeEmail, employeePhone);
        return new EmployeeDto(isMatching);
    }

}
