package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.config.UserDetailsServiceImpl;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.exception.BadRequestException;
import spring.project.groupware.academy.employee.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class CheckDuplicateController {

    private final EmployeeService employeeService;
    private final EntityManager entityManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/employeeId/check")
    public ResponseEntity<?> getCheckIdDuplication(@RequestParam(value = "employeeId") String employeeId) throws BadRequestException {
        System.out.println(employeeId);

        if (employeeService.existsByEmployeeId(employeeId) == true) {
            throw new BadRequestException("이미 사용중인 아이디입니다");
        }else{
            return ResponseEntity.ok("사용가능한 아이디입니다");
        }
    }

//    @GetMapping("/employeeEmail/check")
//    public ResponseEntity<?> getCheckEmailDuplication(@RequestParam(value = "employeeEmail") String employeeEmail) throws BadRequestException {
//
//        System.out.println(employeeEmail);
//
//        if(employeeService.existsByEmployeeEmail(employeeEmail) == true){
//            throw new BadRequestException("이미 사용중인 이메일입니다");
//        }else{
//            return ResponseEntity.ok("사용가능한 이메일입니다");
//        }
//    }

    @GetMapping("/employeeEmail/check")
    public ResponseEntity<?> getCheckEmailDuplication(@RequestParam(value = "employeeEmail") String employeeEmail) throws BadRequestException {
        String jpql = "SELECT COUNT(e) " +
                "FROM EmployeeEntity e " +
                "WHERE e.employeeEmail = :email";

        String semiJpql = "SELECT COUNT(s) " +
                "FROM StudentEntity s " +
                "WHERE s.studentEmail = :email";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
                .setParameter("email", employeeEmail);

        TypedQuery<Long> semiQuery = entityManager.createQuery(semiJpql, Long.class)
                .setParameter("email", employeeEmail);

        Long count = query.getSingleResult() + semiQuery.getSingleResult();

        if (count > 0) {
            throw new BadRequestException("이미 사용중인 이메일입니다");
        } else {
            return ResponseEntity.ok("사용가능한 이메일입니다");
        }
    }

//    @GetMapping("/employeePhone/check")
//    public ResponseEntity<?> getCheckPhoneDuplication(@RequestParam(value = "employeePhone") String employeePhone) throws BadRequestException {
//
//        System.out.println(employeePhone);
//
//        if(employeeService.existsByEmployeePhone(employeePhone) == true){
//            throw new BadRequestException("이미 사용중인 휴대전화번호입니다");
//        }else{
//            return ResponseEntity.ok("사용가능한 휴대전화번호입니다");
//        }
//    }

    @GetMapping("/employeePhone/check")
    public ResponseEntity<?> getCheckPhoneDuplication(@RequestParam(value = "employeePhone") String employeePhone) throws BadRequestException {
        String jpql = "SELECT COUNT(e) " +
                "FROM EmployeeEntity e " +
                "WHERE e.employeePhone = :phone";

        String semiJpql = "SELECT COUNT(s) " +
                "FROM StudentEntity s " +
                "WHERE s.studentPhone = :phone";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
                .setParameter("phone", employeePhone);

        TypedQuery<Long> semiQuery = entityManager.createQuery(semiJpql, Long.class)
                .setParameter("phone", employeePhone);

        Long count = query.getSingleResult() + semiQuery.getSingleResult();

        if (count > 0) {
            throw new BadRequestException("이미 사용중인 휴대전화번호입니다");
        } else {
            return ResponseEntity.ok("사용가능한 휴대전화번호입니다");
        }
    }




}
