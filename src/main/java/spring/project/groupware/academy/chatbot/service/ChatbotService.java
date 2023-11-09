package spring.project.groupware.academy.chatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.chatbot.dto.AnswerDTO;
import spring.project.groupware.academy.chatbot.dto.MessageDTO;
//import spring.project.groupware.academy.chatbot.entity.Intention;
import spring.project.groupware.academy.chatbot.entity.Interest;
//import spring.project.groupware.academy.chatbot.repository.IntentionRepository;
import spring.project.groupware.academy.chatbot.repository.InterestRepository;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotService {

//    private final IntentionRepository intentionRepository;
    private final InterestRepository interestRepository;
    private final MovieService movieService;
    private final BusChatService busChatService;
    private final WeatherChatbotService weatherChatbotService;
    private final Komoran komoran;


    public String getResponse(String message) {
        MessageDTO messageDTO = nlpAnalyze(message);
        return messageDTO.getAnswer().getResponseText();
    }

    public MessageDTO nlpAnalyze(String message) {

        KomoranResult result = komoran.analyze(message);
        log.info("message: {}", message);

        //문자에서 명사들만 추출한 목록 중복제거해서 set
        Set<String> nouns = new HashSet<>(result.getNouns());
        log.info("KOMORAN 분석 결과: {}", result.getList());
        nouns.forEach(noun -> log.info("추출된 명사:" + noun));

        return analyzeToken(nouns, message);
    }

    private MessageDTO analyzeToken(Set<String> nouns, String message) {

        MessageDTO messageDTO = MessageDTO.builder().build();

        String askingAbout = null; // 질문의 범주 (영화, 날씨, 버스)
//        String askingFor = null;
        String city = null; // 송원철

        // 질문의 범주 검사 (영화, 날씨, 버스)
        for (String token : nouns) {
            log.info("범주 - 검사 실행");
            String result = firstAnalyze(token);
            if (result != null) { // firstAnalyze가 null이 아닌 값을 반환하면 askingAbout을 업데이트
                askingAbout = result;
                log.info("범주 : {}", askingAbout);
                break; // 매칭되는 첫번째 범주를 찾으면 반복 중단
            }
        }

        // 질문의 세부 범주 검사 (영화, 날씨, 버스)
//        for (String token : nouns) {
//
//            log.info("세부 범주 - 검사 실행");
//            String result = secondAnalyze(token);
//            if (result != null) { // firstAnalyze가 null이 아닌 값을 반환하면 askingAbout을 업데이트
//                askingFor = result;
//                log.info("세부 범주 : {}", askingFor);
//                break; // 매칭되는 첫번째 범주를 찾으면 반복 중단
//            }
//
//        }

        log.info("askingAbout : {}", askingAbout);

        // api 사용이 필요한지?
        if (isApiRequest(askingAbout)) {
            log.info("api 사용 매핑");
            // 그렇다면 아래 로직 실행.
            String responseFromApi = generateResponseFromApi(message, askingAbout); // 송원철, city 추가
            AnswerDTO answer = AnswerDTO.builder().responseText(responseFromApi).build();
            messageDTO.setAnswer(answer);
        } else {
            log.info("api 사용 매핑 x");
            // 아니라면 일반 답변 생성.
            String response = generateResponse(askingAbout);
            AnswerDTO answer = AnswerDTO.builder().responseText(response).build();
            messageDTO.setAnswer(answer);

        }

        return messageDTO;
    }


    @Transactional(readOnly = true)
    public String firstAnalyze(String token) {

        String[] words = token.split("\\s+");

        List<Interest> interests = interestRepository.findAll();

        for (Interest interest : interests) {
            for (String word : words) {
                if (interest.getKeyword().contains(word)) {
                    return interest.getKeyword();
                }
            }
        }

        return null;
    }
//    @Transactional(readOnly = true)
//    private String secondAnalyze(String token) {
//        List<Intention> intentions = intentionRepository.findAll();
//
//        for (Intention intention : intentions) {
//            if (intention.getKeyword().contains(token)) {
//                return intention.getKeyword();
//            }
//        }
//
//        return null;
//    }

    @Transactional(readOnly = true)
    private boolean isApiRequest(String askingAbout) {
        return interestRepository.findByKeyword(askingAbout)
                .map(Interest::getIsApiRequired)
                .orElse(false);
    }

    private String generateResponseFromApi(String message, String askingAbout) { // 송원철, city 추가

        switch (askingAbout) {
            case "영화":
                return movieService.validMethod(message);
            case "버스":
                return busChatService.BusChat(message);
            case "날씨":
                return weatherChatbotService.getWeatherForCity(message); // 송원철
            default:
                return null;
        }
    }

    private String generateResponse(String askingAbout) {

        return "대충 일반 답변";
    }



}
