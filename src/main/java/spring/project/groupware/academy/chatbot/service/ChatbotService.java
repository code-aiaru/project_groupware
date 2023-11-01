package spring.project.groupware.academy.chatbot.service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.project.groupware.academy.chatbot.dto.AnswerDTO;
import spring.project.groupware.academy.chatbot.dto.MessageDTO;
import spring.project.groupware.academy.chatbot.entity.ChatBotIntention;
import spring.project.groupware.academy.chatbot.repository.ChatBotIntentionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ChatbotService {
        String apiUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=2363357e023a09dc65161918ab04d739";
        String apiUrlWeather = "https://api.openweathermap.org/data/2.5/weather?";
    private final Komoran komoran;
    private final RestTemplate restTemplate;
    @Autowired
    private ChatBotIntentionRepository intention;

    public ChatbotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.komoran = new Komoran(DEFAULT_MODEL.FULL);

    }

    public String getResponse(String message) {
        // 사용자 메시지 분석
        MessageDTO messageDTO = nlpAnalyze(message);
        // 적절한 응답 생성 및 반환
        return messageDTO.getAnswer().getContent();
    }

    public MessageDTO nlpAnalyze(String message) {
        KomoranResult result = komoran.analyze(message);

        // 문자에서 명사들만 추출한 목록 중복 제거해서 set
        Set<String> nouns = new HashSet<>(result.getNouns());
        nouns.forEach(noun -> log.info("추출된 명사: " + noun));

        return analyzeToken(nouns);
    }

    private MessageDTO analyzeToken(Set<String> nouns) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MessageDTO messageDTO = MessageDTO.builder().time(today.format(timeFormatter)).build();

        String askingAbout = null;
        String askingFor = null;

        for (String token : nouns) {
            log.info("1차시 검사 실행");
            askingAbout = firstAnalyze(token);
            log.info("범위 : {}", askingAbout);
            log.info("2차시 검사 실행");
            askingFor = secondAnalyze(token);
            log.info("의도 : {}", askingFor);
        }

        if (askingAbout != null && askingFor != null) {
            String responseFromApi = thirdAnalyze(nouns, askingAbout, askingFor);
            AnswerDTO answer = AnswerDTO.builder().content(responseFromApi).build();
            messageDTO.setAnswer(answer);
            return messageDTO;
        }

        if (messageDTO.getAnswer() == null) {
            AnswerDTO answer = decisionTree("기타", null).orElse(new ChatBotIntention()).getAnswer().toAnswerDTO();
            messageDTO.setAnswer(answer);
        }

        return messageDTO;
    }

    private String firstAnalyze(String token) {
        if (token.contains("영화")) {
            return "영화";
        } else if (token.contains("버스")) {
            return "버스";
        } else if (token.contains("날씨")) {
            return "날씨";
        }
        return null;
    }

    private String secondAnalyze(String token) {
        if (!token.contains("영화")) {
            return token;
        } else if (!token.contains("버스")) {
            return token;
        } else if (!token.contains("날씨")) {
            return token;
        }
        return null;
    }

    private String thirdAnalyze(Set<String> nouns, String askingAbout, String askingFor) {
        switch (askingAbout) {
            case "영화":

                return getDataFromMovieApi();
            case "버스":
//                return busService.busResponse(token);
                return "대충 버스 api로 받아온 값";
            case "날씨":

                return "대충 날씨 api로 받아온 값";
            default:
                return null;
        }
    }



    public String getDataFromMovieApi() {
        LocalDate date = LocalDate.now().minusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDate = date.format(formatter);
        String apiUrl1 = apiUrl + "&targetDt=" + targetDate;
        String botResponse = restTemplate.getForObject(apiUrl1, String.class);

//        String processedData = botResponse;

        return botResponse;
    }

    // 날씨
    public String getDataFromWeatherApi(String cityName) {
        String apiUrl1 = apiUrlWeather + "q=" + cityName + "&appid=31baec95fb6d389a7195e4f5dc84530b";
        String botResponse = restTemplate.getForObject(apiUrl1, String.class);
        return botResponse;
    }

    private Optional<ChatBotIntention> decisionTree(String token, ChatBotIntention upper) {
        return intention.findByNameAndUpper(token, upper);
    }


}
