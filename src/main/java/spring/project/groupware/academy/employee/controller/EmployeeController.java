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
import spring.project.groupware.academy.util.FileStorageService;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    // 사원 Controller

    private final EmployeeService employeeService;
    private final ImageService imageService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    // Create
    @GetMapping({"/join"})
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

    @PostMapping("/join")
    public String postJoin(@Valid @ModelAttribute EmployeeDto employeeDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "employee/join";
        }
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
    @GetMapping({"/manage"})
    public String getEmployeeManage(){
        log.info("employee method activated");
        return "employee/manage";
    }

    // Read - 사원 목록
    @GetMapping("/employeeList")
    public String getEmployeeList(
            @PageableDefault(page=0, size=2, sort = "employeeNo", direction = Sort.Direction.DESC) Pageable pageable,
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

        // 모달창으로 사원추가 시 생년월일 기입 위해 필요
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

        return "employee/employeeList";
    }


    // Detail - 사원 상세 보기
    @GetMapping("/detail/{employeeNo}")
    public String getDetail(@PathVariable("employeeNo") Long employeeNo, Model model){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
        // 이미지 url을 db에서 가져오기
//        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl); // 이미지 url 모델에 추가

        return "employee/detail";
    }


    // Update - 회원 수정 화면
    @GetMapping("/update/{employeeNo}")
    public String getUpdate(@PathVariable("employeeNo") Long employeeNo, EmployeeDto employeeDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

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
//            String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

            model.addAttribute("employee", employee);
//            model.addAttribute("employeeImageUrl", employeeImageUrl);
        }

        model.addAttribute("birthYears", birthYears);
        model.addAttribute("birthMonths", birthMonths);
        model.addAttribute("birthDays", birthDays);

        employeeDto = employeeService.updateViewEmployee(employeeNo);
        model.addAttribute("employeeDto", employeeDto);

        System.out.println("imageUrl : " + employeeDto.getImageUrl()); // 오류 발견 목적으로 써놓음, 지워도 됨

        return "employee/update";
    }


    // Update - 실제 실행
    @PostMapping("/update")
    public String postUpdate(@Valid EmployeeDto employeeDto, BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        if (bindingResult.hasErrors()) {
            System.out.println("유효성 검증 관련 오류 발생");
            return "redirect:/";
        }

        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
        String birthDate = String.format("%04d%02d%02d", employeeDto.getBirthYear(), employeeDto.getBirthMonth(), employeeDto.getBirthDay());

        // MemberDto에 생년월일을 설정합니다.
        employeeDto.setEmployeeBirth(birthDate);

        int rs = employeeService.updateEmployee(employeeDto);

        if (rs == 1) {
            System.out.println("회원정보 수정 성공");

            System.out.println("imageUrl : " + employeeDto.getImageUrl()); // 오류 발견 목적으로 써놓음, 지워도 됨

            return "redirect:/employee/detail/" + employeeDto.getEmployeeNo(); // 수정된 정보를 보여주는 상세 페이지로 이동
        } else {
            System.out.println("회원정보 수정 실패");
            return "redirect:/";
        }
    }


    // Delete - 사원 삭제(관리자(admin 권한)만 가능)
    @GetMapping("/delete/{employeeNo}")
    public String getDelete(@PathVariable("employeeNo") Long employeeNo){

        int rs=employeeService.deleteEmployee(employeeNo);

        if (rs==1) {
            System.out.println("사원 삭제 성공");
            return "redirect:/employee/employeeList?page=0&subject=&search=";

        }else{
            System.out.println("사원 삭제 실패");
            return "redirect:/";
        }
    }


    // 프로필 이미지 변경 페이지
    @GetMapping("/updateImage/{employeeNo}")
    public String getUpdateImage(@PathVariable("employeeNo") Long employeeNo, Model model){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);

        // 이미지 url을 db에서 가져오기
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl); // 이미지 url 모델에 추가

        return "employee/updateImage";
    }


    // 정보 수정 전 비밀번호 확인(비밀번호 변경) - 입력 화면
    @GetMapping("/confirmPassword/password/{employeeNo}")
    public String getConfirmPasswordView(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
//        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);
//        model.addAttribute("employeeImageUrl", employeeImageUrl);

        return "employee/confirmPassword_changePw";
    }

    // 정보 수정 전 비밀번호 확인(사원 삭제) - 입력 화면
    @GetMapping("/confirmPassword/delete/{employeeNo}")
    public String getConfirmPasswordDeleteView(@PathVariable("employeeNo") Long employeeNo, Model model){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
//        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);
//        model.addAttribute("employeeImageUrl", employeeImageUrl);

        return "employee/confirmPassword_delete";
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

    // 현재 관리자의 비밀번호와 DB에 있는 관리자 비밀번호 일치하는지
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

    @PostMapping("/confirmPassword")
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
    @GetMapping("/changePassword/{employeeNo}")
    public String getChangePasswordPage(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
//        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);
//        model.addAttribute("employeeImageUrl", employeeImageUrl);

        return "employee/changePassword"; // changePassword.html 페이지로 이동
    }

    // 비밀번호 변경 실행
    @PostMapping("/changePassword")
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
