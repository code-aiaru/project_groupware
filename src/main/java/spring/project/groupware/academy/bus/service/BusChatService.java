package spring.project.groupware.academy.bus.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.bus.controller.ApiExplorer;
import spring.project.groupware.academy.bus.dto.data.BusJson;
import spring.project.groupware.academy.bus.entity.BusEntity;
import spring.project.groupware.academy.bus.repository.BusRepository;
import spring.project.groupware.academy.bus.service.BusService;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusChatService {

    private final BusRepository busRepository;
    private final BusService busService;
    private final Komoran komoran;

    public String BusChat(String message) {

        KomoranResult result = komoran.analyze(message);
        Set<String> nouns = new HashSet<>(result.getNouns());
        log.info("KOMORAN 분석 결과: {}", result.getList());
        String busNumber = null;

        for (String noun : nouns) {
            KomoranResult analyzeResult = komoran.analyze(noun);
            List<Token> tokenList = analyzeResult.getTokenList();
            for (Token token : tokenList) {
                if ("NNP".equals(token.getPos())) {
                    busNumber = noun;
                    break;
                }
            }
            if (busNumber != null) {
                break;
            } else {
                busNumber = "NNP";
                log.info("NNP를 찾지 못했습니다." + busNumber);
                break;
            }
        }

        Optional<BusEntity> busInfo = busRepository.findBybusRouteNm(busNumber);

        if (!busInfo.isEmpty()) {
            if (message.contains("노선")) {
                return "질문한 버스 고유 노선번호는 : " +
                        busInfo.get().getBusRouteId();
            } else if (message.contains("회사")) {
                return "질문한 버스 회사 정보: " +
                        busInfo.get().getCorpNm();
            } else if (message.contains("거리")) {
                return "질문한 버스 운행 거리는 : " +
                        busInfo.get().getTerm() + "km";
            } else {
                return "질문한 버스 정보: " +
                        busInfo.get().getBusRouteAbrv() + "번은 " +
                        busInfo.get().getStStationNm() + " ~ " +
                        busInfo.get().getEdStationNm() + " 까지 운행합니다.";
            }
        } else {
            try {
                String response = ApiExplorer.getBusList(busNumber);
                BusJson apiJson = mapApiResponseToBusJson(response);
                if (apiJson.getMsgBody().getItemList() == null) {
                    return "조회하는 버스정보 입력오류(고유명사등록 오류)";
                } else {
                    busService.busSaveDBMap(apiJson.getMsgBody().getItemList());
                    Optional<BusEntity> busInfoNo = busRepository.findBybusRouteNm(busNumber);
                    if (message.contains("노선")) {
                        return "질문한 버스 고유 노선번호는 : " +
                                busInfoNo.get().getBusRouteId();
                    } else if (message.contains("회사")) {
                        return "질문한 버스 회사 정보: " +
                                busInfoNo.get().getCorpNm();
                    } else if (message.contains("거리")) {
                        return "질문한 버스 운행 거리는 : " +
                                busInfoNo.get().getTerm() + "km";
                    } else {
                        return "질문한 버스 정보: " +
                                busInfoNo.get().getBusRouteAbrv() + "번은 " +
                                busInfoNo.get().getStStationNm() + " ~ " +
                                busInfoNo.get().getEdStationNm() + " 까지 운행합니다.";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "API 호출 중 오류가 발생했습니다.";
            }
        }
    }

    private BusJson mapApiResponseToBusJson(String response) {
        log.info("API 응답 데이터: " + response);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, BusJson.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}



