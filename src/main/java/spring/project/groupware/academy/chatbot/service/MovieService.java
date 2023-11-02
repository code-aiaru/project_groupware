package spring.project.groupware.academy.chatbot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class MovieService {

    String boxofficeUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?";
    String searchMovieListUrl = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?";
    String searchMovieDetailUrl ="http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";
    String key = "key=2363357e023a09dc65161918ab04d739";

    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String validMethod(String message){

        if (message.contains("현재")||message.contains("지금")||message.contains("상영중")){
            return getDataFromMovieApi();
        }else if (message.contains("오늘")){
            return getDataFromMovieApi();
        }else if (message.contains("정보")){
            return getmoviedetail(message);
        }else {
            return "죄송합니다 질문을 이해하지 못했습니다";
        }

    }


    public String getDataFromMovieApi() {
//        LocalDate date = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate date = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDate = date.format(formatter);
        String apiUrl1 = boxofficeUrl + key + "&targetDt=" + targetDate;


        return restTemplate.getForObject(apiUrl1, String.class);
    }



    public String getmoviedetail(String message) {
        message = message.replace("영화 ", "").replace("정보 ","");

        String apiUrl1 = searchMovieListUrl + key + "&movieNm=" + message;
        String movieList = restTemplate.getForObject(apiUrl1, String.class);
        System.out.println("movieListurl: " + apiUrl1);

        JSONObject listJsonResponse = new JSONObject(movieList);
        JSONArray movieArray = listJsonResponse.getJSONObject("movieListResult").getJSONArray("movieList");
        System.out.println("movieArray: " + movieArray);

        int highestMovieCd = -1;
        String latestOpenDt = "0"; // 초기값 설정

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            String openDt = movie.getString("openDt");
            System.out.println("movieCd: " + movie.getString("movieCd") + ", openDt: " + openDt);

            if (openDt.compareTo(latestOpenDt) > 0) {
                highestMovieCd = Integer.parseInt(movie.getString("movieCd"));
                latestOpenDt = openDt;
            }
        }

        if (highestMovieCd != -1) {
            String apiUrl2 = searchMovieDetailUrl + key + "&movieCd=" + highestMovieCd;
            System.out.println("apiUrl2: " + apiUrl2);
            return restTemplate.getForObject(apiUrl2, String.class);
        } else {
            return "최신 영화를 찾을 수 없습니다.";
        }
    }
}
