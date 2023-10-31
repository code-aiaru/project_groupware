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
    private final WeatherService weatherService;

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

    // 자바 이용 db 저장
    @GetMapping("/weather_java")
    public String weather2(@RequestParam(value = "cityVal", defaultValue = "Seoul") String cityVal, Model model) throws Exception {
        Map<String, String> requestHeaders = new HashMap<>();

        String appid = "31baec95fb6d389a7195e4f5dc84530b";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityVal + "&appid=" + appid;

        System.out.println(apiUrl);

        // OpenApiUtil.get 메서드를 사용하여 API를 호출하고 응답을 가져옴
        String responseBody = OpenApiUtil.get(apiUrl, requestHeaders);

        // API 응답 데이터를 데이터베이스에 저장하기 위해 insertResponseBody 메서드를 호출
        WeatherApiDto weatherApiDto = weatherService.insertResponseBody(responseBody);

        model.addAttribute("weatherApiDto", weatherApiDto);
        model.addAttribute("coord", weatherApiDto.getCoord());
        model.addAttribute("weather", weatherApiDto.getWeather().get(0));

        return "weather/index_java";
    }


}
