package spring.project.groupware.academy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"","/index"})
    public String index(){
        return "index";
    }

    @GetMapping({"/login"})
    public String login(){
        return "login";
    }

    @GetMapping({"/dashboard"})
    public String getDashboard() {
        return "dashboard";
    }
}
