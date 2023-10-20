package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.project.groupware.academy.employee.service.EmployeeService;

// 이메일, 휴대전화번호 이용해 아이디 찾기
@Controller
@RequiredArgsConstructor
public class FindIdController {

    private final EmployeeService employeeService;

    // 이메일과 휴대전화번호를 입력하는 폼 화면
    @GetMapping("/api/findId")
    public String getShowFindIdForm() {
        return "employee/findId";
    }

    @PostMapping("/api/findId")
    @ResponseBody
    public ResponseEntity<String> postFindIdByEmailAndPhone(
            @RequestParam String employeeEmail,
            @RequestParam String employeePhone) {

        String foundId = employeeService.findIdByEmailAndPhone(employeeEmail, employeePhone);

        if (foundId != null) {
            return ResponseEntity.ok(foundId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("IdNotFound");
        }
    }

}
