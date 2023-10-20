//package spring.project.groupware.academy.employee.controller;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import spring.project.groupware.academy.employee.config.MyUserDetails;
//import spring.project.groupware.academy.employee.dto.EmployeeDto;
//
//import javax.validation.Valid;
//
//public class Test {
//    // Create - 실제 실행
//    @PostMapping("/post/employee/join")
//    public String postJoin(@Valid @ModelAttribute EmployeeDto employeeDto, BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return "employee/join";
//        }
//        // 비밀번호 일치 확인
//        if (!employeeDto.getEmployeePassword().equals(employeeDto.getConfirmPassword())) {
//            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지않습니다");
//            return "employee/join";
//        }
//        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
//        String birthDate = String.format("%04d%02d%02d", employeeDto.getBirthYear(), employeeDto.getBirthMonth(), employeeDto.getBirthDay());
//        // employeeDto에 생년월일을 설정합니다.
//        employeeDto.setEmployeeBirth(birthDate);
//        employeeService.insertEmployee(employeeDto);
//        return "redirect:/employee/employeeList?page=0&subject=&search=";
//    }
//    // Update - 실제 실행
//    @PostMapping("/post/employee/update")
//    public String postUpdate(@Valid EmployeeDto employeeDto, BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("유효성 검증 관련 오류 발생");
//            return "redirect:/employee/update/" + employeeDto.getEmployeeNo();
//        }
//        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
//        String birthDate = String.format("%04d%02d%02d", employeeDto.getBirthYear(), employeeDto.getBirthMonth(), employeeDto.getBirthDay());
//        // MemberDto에 생년월일을 설정합니다.
//        employeeDto.setEmployeeBirth(birthDate);
//        int rs = employeeService.updateEmployee(employeeDto);
//        if (rs == 1) {
//            System.out.println("회원정보 수정 성공");
//            return "redirect:/employee/detail/" + employeeDto.getEmployeeNo();
//        } else {
//            System.out.println("회원정보 수정 실패");
//            return "employee/update";
//        }
//    }
//}
