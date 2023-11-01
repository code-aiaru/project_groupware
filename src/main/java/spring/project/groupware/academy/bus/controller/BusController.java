package spring.project.groupware.academy.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusController {

    @GetMapping({"/bus"})
    public String bus() {
        return "bus/index";
    }

}
