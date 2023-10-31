package spring.project.groupware.academy.chatbot.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    private ChatBotIntentionRepository chatBotIntentionRepository;

    private final Komoran komoran;
    private final RestTemplate restTemplate;

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

        //문자에서 명사들만 추출한 목록 중복제거해서 set
        Set<String> nouns = new HashSet<>(result.getNouns());
        log.info("KOMORAN 분석 결과: {}", result.getList());

        nouns.forEach(noun -> log.info("추출된 명사:" + noun));

        return analyzeToken(nouns);
    }

    private MessageDTO analyzeToken(Set<String> nouns) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MessageDTO messageDTO = MessageDTO.builder().time(today.format(timeFormatter)).build();

        String askingAbout = null;
        String askingFor = null;

        for (String token : nouns) {
//                 Optional<ChatBotIntention> result = decisionTree(token, null);
//                 if (result.isEmpty()) continue;
//                 Set<String> next = new HashSet<>(nouns);
//                 next.remove(token);


            AnswerDTO answer = decisionTree(token).orElse(new ChatBotIntention()).getAnswer().toAnswerDTO();
            messageDTO.setAnswer(answer);



            log.info("1차시 검사 실행");
            if (firstAnalyze(token) != null) {
                askingAbout = firstAnalyze(token);
                log.info("범위 : {}", askingAbout);
//                    continue;
            }

            log.info("2차시 검사 실행");
            if (secondAnalyze(token) != null) {
                askingFor = secondAnalyze(token);
                log.info("의도 : {}", askingFor);
            }

        }

        if (askingAbout != null && askingFor != null) {
            String responseFromApi = thirdAnalyze(nouns, askingAbout, askingFor);
            AnswerDTO answer = AnswerDTO.builder().content(responseFromApi).build();
            messageDTO.setAnswer(answer);
            return messageDTO;
        }

        if (messageDTO.getAnswer() == null){
            AnswerDTO answer = decisionTree("기타").orElse(new ChatBotIntention()).getAnswer().toAnswerDTO();
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
        if (!token.contains("영화") && !token.contains("버스") && !token.contains("날씨")) {
            return token;
        }
        return null;
    }

    private String thirdAnalyze(Set<String> nouns, String askingAbout, String askingFor) {
        switch (askingAbout) {
            case "영화":
//                return movieService.movieResponse(token);
                return getDataFromMovieApi();
            case "버스":
//                return busService.busResponse(token);
                return "대충 버스 api로 받아온 값";
            case "날씨":
//                return weatherService.weatherResponse(token);
                return "대충 날씨 api로 받아온 값";
            default:
                return null;
        }
    }

    public String getDataFromMovieApi() {
        String apiUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=2363357e023a09dc65161918ab04d739";
        LocalDate date = LocalDate.now().minusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDate = date.format(formatter);
        String apiUrl1 = apiUrl + "&targetDt=" + targetDate;
        String botResponse = restTemplate.getForObject(apiUrl1, String.class);

//        String processedData = botResponse;

        return botResponse;
    }
//
//    private String processMovieData(String rawData) {
//        // rawData를 JSON 객체로 파싱
//        JsonObject jsonObject = new JsonParser().parse(rawData).getAsJsonObject();
//
//        // 필요한 데이터 추출 및 가공
//        JsonObject boxOfficeResult = jsonObject.getAsJsonObject("boxOfficeResult");
//        String boxOfficeType = boxOfficeResult.get("boxofficeType").getAsString();
//        String showRange = boxOfficeResult.get("showRange").getAsString();
//
//        JsonArray weeklyBoxOfficeList = boxOfficeResult.getAsJsonArray("weeklyBoxOfficeList");
//
//        StringBuilder result = new StringBuilder();
//        result.append("주간 박스오피스 유형: ").append(boxOfficeType).append("<br>");
//        result.append("상영 기간: ").append(showRange).append("<br>");
//
//        for (JsonElement movieElement : weeklyBoxOfficeList) {
//            JsonObject movie = movieElement.getAsJsonObject();
//            String movieName = movie.get("movieNm").getAsString();
//            String rank = movie.get("rank").getAsString();
//            String salesAmt = movie.get("salesAmt").getAsString();
//            String audiCnt = movie.get("audiCnt").getAsString();
//
//            result.append("영화: ").append(movieName).append("<br>");
//            result.append("순위: ").append(rank).append("<br>");
//            result.append("매출액: ").append(salesAmt).append("<br>");
//            result.append("관객 수: ").append(audiCnt).append("<br>");
//            result.append("<br>");
//        }
//
//        return result.toString();
//    }


    //의도가 존재하는지 DB에서 파악
    // 안녕 -> 등록
//    private Optional<ChatBotIntention> decisionTree(String token, ChatBotIntention upper) {
//        return chatBotIntentionRepository.findByNameAndUpper(token, upper);
//    }

    private Optional<ChatBotIntention> decisionTree(String token) {
        return chatBotIntentionRepository.findByName(token);
    }


}
