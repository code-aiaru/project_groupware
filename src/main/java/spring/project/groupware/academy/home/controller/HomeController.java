package spring.project.groupware.academy.home.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;
import spring.project.groupware.academy.employee.service.ImageService;
import spring.project.groupware.academy.weather.OpenApiUtil;
import spring.project.groupware.academy.weather.WeatherApiDto;
import spring.project.groupware.academy.weather.WeatherService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EmployeeService employeeService;
    private final ImageService imageService;

    @GetMapping({"", "/index"})
    public String index(@AuthenticationPrincipal MyUserDetails myUserDetails,
                        HttpServletRequest request,  Model model){

//        if(myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
            String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

            model.addAttribute("employee", employee);
            model.addAttribute("employeeImageUrl", employeeImageUrl);

            // 요청 속성에서 previousURL 검색
            String previousURL = (String) request.getAttribute("previousURL");
            if (previousURL != null) {
                System.out.println("previousURL: " + previousURL);
                model.addAttribute("previousURL", previousURL);
            }
            log.info("Redirected to index page");
            return "index"; // 로그인 돼있다면 index 페이지로 이동
        }
//        log.info("Redirected to login page");
//        return "login"; // 로그인 안돼있으면 로그인 페이지로 이동
//    }

    @GetMapping({"/login"})
    public String login(){
        log.info("login activated");
        return "login";
    }

    @GetMapping({"/dashboard"})
    public String getDashboard() {
        log.info("GetMapped : dashboard");
        return "dashboard/dashboard";
    }

    @GetMapping("/weather")
    public String weather(){
        return "weather/index";
    }

    @GetMapping("/weather2")
    public String weather2(){
        return "weather/index_java";
    }



}
