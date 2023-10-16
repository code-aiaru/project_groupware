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


    // 관리자 비밀번호 확인
    @PostMapping("/checkAdminPassword")
    @ResponseBody
    public Map<String, Boolean> postCheckAdminPassword(@RequestParam("currentPassword") String currentPassword) {

        // 현재 로그인한 유저의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String employeeId = userDetails.getUsername();

            // username을 이용하여 현재 로그인한 유저의 정보를 가져옴 (UserDetailsService 사용)
            MyUserDetails currentUserDetails = (MyUserDetails) userDetailsService.loadUserByUsername(employeeId);

            // 현재 입력한 비밀번호와 현재 로그인한 유저의 비밀번호를 비교
            boolean valid = passwordEncoder.matches(currentPassword, currentUserDetails.getPassword());

            Map<String, Boolean> response = new HashMap<>();
            response.put("valid", valid);
            return response;
        } else {
            // 사용자가 로그인하지 않은 경우 또는 인증이 실패한 경우
            Map<String, Boolean> response = new HashMap<>();
            response.put("valid", false);
            return response;
        }
    }

    // 입력한 현재비밀번호와 DB에 있는 현재비밀번호 일치하는지
    @PostMapping("/checkCurrentPassword")
    @ResponseBody
    public Map<String, Boolean> postCheckCurrentPassword(@RequestParam("currentPassword") String currentPassword,
                                                         @RequestParam("employeeNo") Long employeeNo) {

        boolean valid = employeeService.checkCurrentPassword(employeeNo, currentPassword);

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);
        return response;
    }



}
