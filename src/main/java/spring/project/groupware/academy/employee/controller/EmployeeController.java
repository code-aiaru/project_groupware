package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.config.UserDetailsServiceImpl;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;
import spring.project.groupware.academy.employee.service.ImageService;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("/employee")
public class EmployeeController {

    // 사원 Controller

    private final EmployeeService employeeService;
    private final ImageService imageService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    // Create
    @GetMapping({"/employee/join"})
    public String getJoin(EmployeeDto employeeDto, Model model){

        // 연도, 월, 일 데이터를 모델에 추가하여 뷰로 전달
        List<Integer> birthYears = new ArrayList<>();
        for (int year = 2023; year >= 1900; year--) { // 2023부터 1900까지 역순으로 추가
            birthYears.add(year);
        }
        List<Integer> birthMonths = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            birthMonths.add(month);
        }
        List<Integer> birthDays = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            birthDays.add(day);
        }

        model.addAttribute("birthYears", birthYears);
        model.addAttribute("birthMonths", birthMonths);
        model.addAttribute("birthDays", birthDays);

        log.info("employee method activated");

        return "employee/join";
    }

    @PostMapping("/api/employee/join")
    public String postJoin(@Valid @ModelAttribute EmployeeDto employeeDto, BindingResult bindingResult){

        // 사원 추가 시 오류 때문에 주석처리 해놓음
//        if (bindingResult.hasErrors()) {
//            return "redirect:/employee/detail/" + employeeDto.getEmployeeNo();
//        }
        // 비밀번호 일치 확인
        if (!employeeDto.getEmployeePassword().equals(employeeDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지않습니다");
            return "employee/join";
        }

        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
        String birthDate = String.format("%04d%02d%02d", employeeDto.getBirthYear(), employeeDto.getBirthMonth(), employeeDto.getBirthDay());

        // employeeDto에 생년월일을 설정합니다.
        employeeDto.setEmployeeBirth(birthDate);

        employeeService.insertEmployee(employeeDto);

        return "redirect:/employee/employeeList?page=0&subject=&search=";
    }

    // 로그인 화면
//    @GetMapping("/login")
//    public String getLogin(){
//        return "login";
//    }

    // 인사관리 화면
    @GetMapping({"/employee/manage"})
    public String getEmployeeManage(){
        log.info("employee method activated");
        return "employee/manage";
    }

    // Read - 사원 목록(admin만 조회 가능)
    @GetMapping("/employee/employeeList")
    public String getEmployeeList(
            @PageableDefault(page=0, size=5, sort = "employeeNo", direction = Sort.Direction.DESC) Pageable pageable,
            Model model,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "search", required = false) String search,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {

        if (myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
//            String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

            model.addAttribute("employee", employee);
//            model.addAttribute("employeeImageUrl", employeeImageUrl);
            model.addAttribute("myUserDetails", myUserDetails);
        }

        Page<EmployeeDto> employeeList = employeeService.employeeList(pageable, subject, search);

        Long totalCount = employeeList.getTotalElements();
        int totalPage = employeeList.getTotalPages();
        int pageSize = employeeList.getSize();
        int nowPage = employeeList.getNumber();
        int blockNum = 10;

        int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ?
                (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "employee/employeeList";
    }

    // 간단한 사원목록(일반사원 조회 가능)
    @GetMapping("/employee/simpleEmployeeList")
    public String getSimpleEmployeeList(
            @PageableDefault(page=0, size=5, sort = "employeeNo", direction = Sort.Direction.DESC) Pageable pageable,
            Model model,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "search", required = false) String search,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {

        if (myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());

            model.addAttribute("employee", employee);
            model.addAttribute("myUserDetails", myUserDetails);
        }

        Page<EmployeeDto> employeeList = employeeService.employeeList(pageable, subject, search);

        Long totalCount = employeeList.getTotalElements();
        int totalPage = employeeList.getTotalPages();
        int pageSize = employeeList.getSize();
        int nowPage = employeeList.getNumber();
        int blockNum = 10;

        int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ?
                (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "employee/simpleEmployeeList";
    }


    // Detail - 사원 상세 보기
    @GetMapping("/employee/detail/{employeeNo}")
    public String getDetail(@PathVariable("employeeNo") Long employeeNo, Model model,
                            @AuthenticationPrincipal MyUserDetails myUserDetails){

        // 현재 사용자 권한이 admin인지 확인, admin 아니라면 true 반환
        if (!myUserDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
                return "error";
            }
        }

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl); // 이미지 url 모델에 추가
        return "employee/detail";
    }


    // Update - 회원 수정 화면
    @GetMapping("/employee/update/{employeeNo}")
    public String getUpdate(@PathVariable("employeeNo") Long employeeNo, EmployeeDto employeeDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        if (!myUserDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
                return "error";
            }
        }

        // 연도, 월, 일 데이터를 모델에 추가하여 뷰로 전달
        List<Integer> birthYears = new ArrayList<>();
        for (int year = 2023; year >= 1900; year--) { // 2023부터 1900까지 역순으로 추가
            birthYears.add(year);
        }
        List<Integer> birthMonths = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            birthMonths.add(month);
        }
        List<Integer> birthDays = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            birthDays.add(day);
        }

        if (myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(employeeNo);
            model.addAttribute("employee", employee);
        }

        model.addAttribute("birthYears", birthYears);
        model.addAttribute("birthMonths", birthMonths);
        model.addAttribute("birthDays", birthDays);

        employeeDto = employeeService.updateViewEmployee(employeeNo);
        model.addAttribute("employeeDto", employeeDto);

        return "employee/update";
    }


    // Update - 실제 실행
    @PostMapping("/post/employee/update")
    public String postUpdate(@Valid EmployeeDto employeeDto, BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {

//        if (bindingResult.hasErrors()) {
//            System.out.println("유효성 검증 관련 오류 발생");
//            return "redirect:/employee/update/" + employeeDto.getEmployeeNo();
//        }

        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
        String birthDate = String.format("%04d%02d%02d", employeeDto.getBirthYear(), employeeDto.getBirthMonth(), employeeDto.getBirthDay());

        // MemberDto에 생년월일을 설정합니다.
        employeeDto.setEmployeeBirth(birthDate);

        int rs = employeeService.updateEmployee(employeeDto);

        if (rs == 1) {
            System.out.println("회원정보 수정 성공");
            return "redirect:/employee/detail/" + employeeDto.getEmployeeNo();

        } else {
            System.out.println("회원정보 수정 실패");
            return "employee/update";
        }
    }


    // Delete - 사원 삭제(관리자(admin 권한)만 가능)
    @GetMapping("/employee/delete/{employeeNo}")
    public String getDelete(@PathVariable("employeeNo") Long employeeNo, @AuthenticationPrincipal MyUserDetails myUserDetails){

        if (!myUserDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
                return "error";
            }
        }

        int rs=employeeService.deleteEmployee(employeeNo);

//        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
//        model.addAttribute("employee", employee.getEmployeeNo());

        if (rs==1) {
            System.out.println("사원 삭제 성공");
            return "redirect:/employee/employeeList?page=0&subject=&search=";

        }else{
            System.out.println("사원 삭제 실패");
            return "redirect:/";
        }
    }


    // 프로필 이미지 변경 페이지
    @GetMapping("/employee/updateImage/{employeeNo}")
    public String getUpdateImage(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
            return "error";
        }

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);

        // 이미지 url을 db에서 가져오기
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl); // 이미지 url 모델에 추가

        return "employee/updateImage";
    }


    // 정보 수정 전 비밀번호 확인(비밀번호 변경) - 입력 화면
    @GetMapping("/employee/confirmPassword/password/{employeeNo}")
    public String getConfirmPasswordView(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
            return "error";
        }

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);

        return "employee/confirmPassword_changePw";
    }

    // 사원 삭제 전 비밀번호 확인(사원 삭제) - 입력 화면
    @GetMapping("/employee/confirmPassword/delete/{employeeNo}")
    public String getConfirmPasswordDeleteView(@PathVariable("employeeNo") Long employeeNo, Model model,
                                               @AuthenticationPrincipal MyUserDetails myUserDetails){

        if (!myUserDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
                return "error";
            }
        }

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);

        return "employee/confirmPassword_delete";
    }

    // 입력한 현재비밀번호와 DB에 있는 현재비밀번호 일치하는지
    @PostMapping("/api/employee/checkCurrentPassword")
    @ResponseBody
    public Map<String, Boolean> postCheckCurrentPassword(@RequestParam("currentPassword") String currentPassword,
                                                         @RequestParam("employeeNo") Long employeeNo) {

        boolean valid = employeeService.checkCurrentPassword(employeeNo, currentPassword);

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);
        return response;
    }

    // 현재 관리자의 비밀번호와 DB에 있는 관리자 비밀번호 일치하는지
    @PostMapping("/api/employee/checkAdminPassword")
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

    @PostMapping("/api/employee/confirmPassword")
    public String postConfirmPassword(@RequestParam("currentPassword") String currentPassword,
                                      @RequestParam("employeeNo") Long employeeNo,
                                      EmployeeDto employeeDto){

        boolean valid=employeeService.checkCurrentPassword(employeeNo, currentPassword);

        if (valid) {
            return "redirect:/employee/changePassword/" + employeeDto.getEmployeeNo(); // 비밀번호 수정 페이지로 이동
        } else {
            return "redirect:/employee/confirmPassword/password/" + employeeDto.getEmployeeNo(); // 다시 비밀번호 확인 페이지로 이동
        }
    }

    // 비밀번호 변경 페이지
    @GetMapping("/employee/changePassword/{employeeNo}")
    public String getChangePasswordPage(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        if (!myUserDetails.getEmployeeEntity().getEmployeeNo().equals(employeeNo)) {
            return "error";
        }

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);

        return "employee/changePassword";
    }

    // 비밀번호 변경 실행
    @PostMapping("/api/employee/changePassword")
    public String postChangePassword(@RequestParam("employeeNo") Long employeeNo,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmNewPassword") String confirmNewPassword,
                                     EmployeeDto employeeDto, Model model) {

        // 새로운 비밀번호 확인과 일치하는지 검사
        if (!newPassword.equals(confirmNewPassword)) {
            // 일치하지 않을 때 처리 (예: 오류 메시지 표시)
            return "redirect:/employee/changePassword"; // 비밀번호 변경 페이지로 다시 이동
        }

        // 비밀번호 변경 메서드 호출
        boolean success = employeeService.changePassword(employeeNo, currentPassword, newPassword, employeeDto);

        if (success) {
            // 비밀번호 변경 성공 시 처리 (예: 메시지 표시)
            System.out.println("비밀번호 변경 성공");
            return "redirect:/employee/detail/" + employeeDto.getEmployeeNo(); // 수정된 정보를 보여주는 상세 페이지로 이동
        } else {
            // 비밀번호 변경 실패 시 처리 (예: 오류 메시지 표시)
            System.out.println("비밀번호 변경 실패");
            return "redirect:/employee/changePassword"; // 비밀번호 변경 페이지로 다시 이동
        }
    }


}
