package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;
import spring.project.groupware.academy.employee.service.ImageServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    // 사원 Controller

    private final EmployeeService employeeService;
    private final ImageServiceImpl imageService;

    // Create
    @GetMapping("/join")
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
        return "login";
    }

    // 로그인 화면
//    @GetMapping("/login")
//    public String getLogin(){
//        return "login";
//    }


    // Detail - 사원 상세 보기
    @GetMapping("/detail/{employeeNo}")
    public String getDetail(@PathVariable("employeeNo") Long employeeNo, Model model){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
        // 이미지 url을 db에서 가져오기
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
            String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

            model.addAttribute("employee", employee);
            model.addAttribute("employeeImageUrl", employeeImageUrl);
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


    // 정보 수정 전 비밀번호 확인 - 입력 화면
    @GetMapping("/confirmPassword/{employeeNo}")
    public String getConfirmPasswordView(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl);

        return "employee/confirmPassword";
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

    @PostMapping("/confirmPassword")
    public String postConfirmPassword(@RequestParam("currentPassword") String currentPassword,
                                      @RequestParam("employeeNo") Long employeeNo,
                                      EmployeeDto employeeDto){

        boolean valid=employeeService.checkCurrentPassword(employeeNo, currentPassword);

        if (valid) {
            return "redirect:/employee/changePassword/" + employeeDto.getEmployeeNo(); // 비밀번호 수정 페이지로 이동
        } else {
            return "redirect:/employee/confirmPassword/" + employeeDto.getEmployeeNo(); // 다시 비밀번호 확인 페이지로 이동
        }
    }

    // 비밀번호 변경 페이지
    @GetMapping("/changePassword/{employeeNo}")
    public String getChangePasswordPage(@PathVariable("employeeNo") Long employeeNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        EmployeeDto employee = employeeService.detailEmployee(employeeNo);
        String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

        model.addAttribute("employeeNo", employeeNo);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeImageUrl", employeeImageUrl);

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
