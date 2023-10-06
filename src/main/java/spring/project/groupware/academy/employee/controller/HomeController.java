//package spring.project.groupware.academy.employee.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import spring.project.groupware.academy.employee.config.MyUserDetails;
//import spring.project.groupware.academy.employee.dto.EmployeeDto;
//import spring.project.groupware.academy.employee.service.EmployeeService;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;
//
//@RequiredArgsConstructor
//@Controller
//public class HomeController {
//
//    private final EmployeeService employeeService;
//    private final ImageServiceImpl imageService;
//
////    @GetMapping({"", "/index"})
////    public String index(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
////
////        if(myUserDetails != null) {
////            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
////            model.addAttribute("employee", employee);
////        }
////        return "index";
////    }
//
//}
