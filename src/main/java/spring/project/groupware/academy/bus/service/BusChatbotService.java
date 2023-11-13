package spring.project.groupware.academy.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.project.groupware.academy.bus.controller.ApiExplorer;
import spring.project.groupware.academy.bus.dto.data.BusJson;
import spring.project.groupware.academy.bus.entity.BusEntity;
import spring.project.groupware.academy.bus.entity.StationEntity;
import spring.project.groupware.academy.bus.repository.BusRepository;
import spring.project.groupware.academy.bus.repository.StationRepository;
import spring.project.groupware.academy.bus.service.BusChatService;
import spring.project.groupware.academy.bus.service.BusService;
import spring.project.groupware.academy.chatbot.dto.MessageDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusChatbotService {

    private final BusRepository busRepository;
    private final StationRepository stationRepository;
    private final BusService busService;
    private final BusChatService busChatService;


    public String BusSearch(String message) {
        //입력 값에서 버스 번호 구분
//        String busNum = BusNumberDiv(message);

        String busInfo = message.replace("버스", "").trim();

        System.out.println("busInfo print : "+busInfo);
        log.info("busInfo log : "+busInfo);

        if (busInfo.contains("정류장")) {
            busInfo = busInfo.replace("정류장", "").trim();
            return StationDbSave(busInfo);
        } else if (busInfo.contains("시간")) {
            busInfo = busInfo.replace("시간", "").trim();
//            return stationTime(busInfo);
            return null;
        } else {
            return busChatService.BusChat(message);
        }

    }

    // 버스 번호 구분 앞에만 붙는걸 생각했는데 뒤에 붙는 경우도 있어서 수정 필요
    public String BusNumberDiv(String message) {
        String regex = "[가-힣]{2}\\d+\\-\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        StringBuilder extractedCharacters = new StringBuilder();
        while (matcher.find()) {
            extractedCharacters.append(matcher.group());
        }
        return extractedCharacters.toString();
    }

    // 버스 기본 정보 조회,저장
    public String BusDbSave(String busRouteNm) {
        String busId = "";

        if (busRouteNm.equals("") || busRouteNm.equals(null)) {
//            return "입력 값이 비었습니다. 다시 확인 해주세요. ";
            return null;
        }

        Optional<BusEntity> busInfo = busRepository.findBybusRouteNm(busRouteNm);

        if (!busInfo.isEmpty()) {
            busId = busInfo.get().getBusRouteId();
            return busId;
        } else {
            try {
                // ApiExplorer의 getBusList 메서드 호출
                String response = ApiExplorer.getBusList(busRouteNm);

                if (response == null) {
//                    return "검색된 버스 노선이 없습니다. 다시 확인 해주세요. ";
                    return null;
                }

                // 응답 데이터를 BusJson 객체로 매핑 (예시, 실제로는 적절한 매핑 로직이 필요함)
                BusJson apiJson = mapApiResponseToBusJson(response);
                busService.busSaveDBMap(apiJson.getMsgBody().getItemList());
                Optional<BusEntity> busInfo2 = busRepository.findBybusRouteNm(busRouteNm);

                if (busInfo2.isEmpty()) {           // ***** isPresent()>>isEmpty()
                    System.out.println("데이터베이스에 노선 정보 저장 중 오류 발생");
                    return null;
                }
                // 저장된 노선 정보의 ID를 할당
                busId = busInfo2.get().getBusRouteId();
            } catch (IOException e) {
                // 예외 처리
                e.printStackTrace();
                System.out.println("API 호출 중 오류가 발생했습니다.");
                return null;
            }
        }
        return busId;
    }

    // null 오류 문제 해결
    public String StationDbSave(String busRouteNm) {
        String response = "";

        if (busRouteNm == null || busRouteNm.isEmpty()) {
            return "입력 값이 비었습니다. 다시 확인 해주세요. ";
        }
        // busRouteId를 얻어오는 로직
        String busRouteId = BusDbSave(busRouteNm);
        // BusDbSave에서 null이 반환된 경우 에러 메시지 반환
        if (busRouteId == null) {
            return "검색된 버스 노선이 없습니다. 다시 확인 해주세요. ";
        }

        // API 호출 및 데이터베이스 저장
        try {
            String response2 = ApiExplorer.getBustStaionPost(busRouteId);
            BusJson apiJson2 = mapApiResponseToBusJson(response2);
            busService.staionSaveDBMap(apiJson2.getMsgBody().getItemList());
            // 저장된 정류소 정보를 조회       //노선 이름만 검색 시 일부만 받는 문제로 순번 추가      // 수정 필요
            Optional<StationEntity> stationInfo = stationRepository.findBybusRouteNmAndSeq(busRouteNm, "1");
            // 조회된 정류소 정보가 있으면 해당 정보의 ID를 반환
            if (stationInfo.isPresent()) {
                response = response2;
            } else {
                System.out.println("데이터베이스에 정류소 정보 저장 중 오류 발생");
                // 에러 처리
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("API 호출 중 오류가 발생했습니다.");
            // 에러 처리
        }
        return response;
    }


    public String stationTime(String busRouteId, String stId, String ord){
//        String stId = "";
//        String ord = "";
//        String busRouteId = "";

//        StationEntity station = stationRepository.findBybusRouteNmAndStation(busRouteId, stId);
//        ord = station.getSeq();

        // API 호출
        try {
            String response = ApiExplorer.busArrOne(busRouteId, stId, ord);

            if (!response.isEmpty()) {                          //
                return response;
            } else {
                // 에러 처리 로직 추가 (예: 에러 메시지 반환 또는 예외 던지기)
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("API 호출 중 오류가 발생했습니다.");
        }
        return null;
    }


    // 응답 데이터를 BusJson 객체로 매핑
    private BusJson mapApiResponseToBusJson(String response) {
        // response 문자열을 파싱하여 BusJson 객체로 변환
        log.info("API 응답 데이터: " + response);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, BusJson.class);
        } catch (JsonProcessingException e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

}
