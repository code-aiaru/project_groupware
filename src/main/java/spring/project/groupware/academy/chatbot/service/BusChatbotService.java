//package spring.project.groupware.academy.chatbot.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import spring.project.groupware.academy.bus.controller.ApiExplorer;
//import spring.project.groupware.academy.bus.dto.data.BusJson;
//import spring.project.groupware.academy.bus.entity.BusEntity;
//import spring.project.groupware.academy.bus.repository.BusRepository;
//import spring.project.groupware.academy.bus.service.BusService;
//import spring.project.groupware.academy.chatbot.dto.MessageDTO;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class BusChatbotService {
//
//    private final BusRepository busRepository;
//
//    private final RestTemplate restTemplate;
//    private final ChatbotService chatbotService;
//
//    private final BusService busService;
//
//    public String getResponseBusNum(String message) {
//        MessageDTO messageDTO=chatbotService.nlpAnalyze(message);
//
//        if (message.startsWith("버스")) {
//            String busNumber = message.substring(2).trim(); // 임시, "버스 00번" 질문시 "버스" 제거
//            Optional<BusEntity> busInfo = busRepository.findBybusRouteNm(busNumber);
//
//            if (!busInfo.isEmpty()) {
//                return "질문한 버스 정보: " +
//                        busInfo.get().getBusRouteAbrv() + "번은 " + "\n" +
//                        busInfo.get().getStStationNm() + " ~ " +
//                        busInfo.get().getEdStationNm() + " 까지 운행합니다." + "\n" +
//                        "";
//            } else {
//                try {
//                    // ApiExplorer의 getBusList 메서드 호출
//                    String response = ApiExplorer.getBusList(busNumber);
//
//                    // 응답 데이터를 BusJson 객체로 매핑 (예시, 실제로는 적절한 매핑 로직이 필요함)
//                    BusJson apiJson = mapApiResponseToBusJson(response);
//
//                    // 데이터베이스에 저장하고 응답을 받아옴
////                            Map<String, String> DBresponse = busService.busSaveDBMap(apiJson.getMsgBody().getItemList());
//                    busService.busSaveDBMap(apiJson.getMsgBody().getItemList());
//
////                            log.info("API 응답 데이터: " + response);
//
////                            // 응답 처리 로직 추가
////                            return DBresponse.toString(); // 적절한 응답 포맷을 반환하도록 변경
//                    Optional<BusEntity> busInfo2 = busRepository.findBybusRouteNm(busNumber);
//                    if (busInfo2.isPresent()) {
//                        return "질문한 버스 정보: " +
//                                busInfo2.get().getBusRouteNm() + "번은 " + "\n" +
//                                busInfo2.get().getStStationNm() + " ~ " +
//                                busInfo2.get().getEdStationNm() + " 까지 운행합니다." + "\n" +
//                                "";
//                    } else {
//                        return "조회하는 버스정보 입력오류";
//                    }
//                } catch (IOException e) {
//                    // 예외 처리
//                    e.printStackTrace();
//                    return "API 호출 중 오류가 발생했습니다.";
//                }
//            }
//        }
//        return "무언가 어디서 잘못됨";
//    }
//
//    // 응답 데이터를 BusJson 객체로 매핑
//    private BusJson mapApiResponseToBusJson(String response) {
//        // response 문자열을 파싱하여 BusJson 객체로 변환
//        log.info("API 응답 데이터: " + response);
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(response, BusJson.class);
//        } catch (JsonProcessingException e) {
//            // 예외 처리
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
