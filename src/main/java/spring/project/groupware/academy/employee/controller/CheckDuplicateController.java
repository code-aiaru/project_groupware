package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.employee.exception.BadRequestException;
import spring.project.groupware.academy.employee.service.EmployeeService;

import javax.persistence.EntityManager;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class CheckDuplicateController {

    private final EmployeeService employeeService;
    private final EntityManager entityManager;

    @GetMapping("/employeeId/check")
    public ResponseEntity<?> getCheckIdDuplication(@RequestParam(value = "employeeId") String employeeId) throws BadRequestException {
        System.out.println(employeeId);

        if (employeeService.existsByEmployeeId(employeeId) == true) {
            throw new BadRequestException("이미 사용중인 아이디입니다");
        }else{
            return ResponseEntity.ok("사용가능한 아이디입니다");
        }
    }

    @GetMapping("/employeeEmail/check")
    public ResponseEntity<?> getCheckEmailDuplication(@RequestParam(value = "employeeEmail") String employeeEmail) throws BadRequestException {

        System.out.println(employeeEmail);

        if(employeeService.existsByEmployeeEmail(employeeEmail) == true){
            throw new BadRequestException("이미 사용중인 이메일입니다");
        }else{
            return ResponseEntity.ok("사용가능한 이메일입니다");
        }
    }

    @GetMapping("/employeePhone/check")
    public ResponseEntity<?> getCheckPhoneDuplication(@RequestParam(value = "employeePhone") String employeePhone) throws BadRequestException {

        System.out.println(employeePhone);

        if(employeeService.existsByEmployeePhone(employeePhone) == true){
            throw new BadRequestException("이미 사용중인 휴대전화번호입니다");
        }else{
            return ResponseEntity.ok("사용가능한 휴대전화번호입니다");
        }
    }

}
