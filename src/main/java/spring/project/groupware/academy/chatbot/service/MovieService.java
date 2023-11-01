package spring.project.groupware.academy.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Service
@RequiredArgsConstructor
public class MovieService {
    String apiUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=2363357e023a09dc65161918ab04d739";

    private final RestTemplate restTemplate;



    public String getDataFromMovieApi() {
//        LocalDate date = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate date = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDate = date.format(formatter);
        String apiUrl1 = apiUrl + "&targetDt=" + targetDate;
        String botResponse = restTemplate.getForObject(apiUrl1, String.class);

//        String processedData = botResponse;

        return botResponse;
    }





}
