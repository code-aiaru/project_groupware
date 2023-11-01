package spring.project.groupware.academy.bus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.bus.dto.data.BusJson;
import spring.project.groupware.academy.bus.service.BusService;

import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusApiController {

    private final BusService busService;

    @PostMapping("/saveDB")
    public Map<String, String> saveDB(@RequestBody BusJson apiJson) {
        Map<String, String> response = busService.busSaveDBMap(apiJson.getMsgBody().getItemList());
        return response;
    }
}
