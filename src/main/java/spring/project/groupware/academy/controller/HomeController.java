package spring.project.groupware.academy.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EmployeeService employeeService;
//    private final ImageServiceImpl imageService;

//    @GetMapping({"", "/index"})
//    public String index(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
//
//        if(myUserDetails != null) {
//            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
//            model.addAttribute("employee", employee);
//        }
//        return "index";
//    }

    @GetMapping({"", "/index"})
    public String index(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){

        if(myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
            model.addAttribute("employee", employee);
            return "index"; // 로그인 돼있다면 index 페이지로 이동
        }
        return "login"; // 로그인 안돼있으면 로그인 페이지로 이동
    }

    @GetMapping({"/login"})
    public String login(){
        return "login";
    }

    @GetMapping({"/dashboard"})
    public String getDashboard(){
        return "dashboard";
    }

    @GetMapping({"/student/manage"})
    public String getStudentManage() {
        return "student/manage";
    }










}
