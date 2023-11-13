package spring.project.groupware.academy.bus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/bus/stationTime")
    public ResponseEntity<String> getBusTime(@RequestBody Map<String, String> requestData){

        try{
            String seq = requestData.get("seq");
            String station = requestData.get("station");
            String busRouteId = requestData.get("busRouteId");

            String response = ApiExplorer.busArrOne(busRouteId, station, seq);

            return ResponseEntity.ok(response);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러발생");
        }
    }

}
