package spring.project.groupware.academy.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.project.groupware.academy.weather.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class WeatherChatbotService {

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    public String saveWeatherDataForCity(String message) {
           String newMessage = message.replace("날씨", "");
        String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather";
        String apiKey = "31baec95fb6d389a7195e4f5dc84530b";


        System.out.println("newMessage :"+newMessage);

        String apiUrl = weatherApiUrl + "?q=" + newMessage + "&appid=" + apiKey;
        log.info("apiUrl : " + apiUrl);

        // API 호출 및 응답 데이터 가져오기
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // API 응답 데이터를 DB에 저장
            insertResponseBody(response.getBody());
            log.info("API 응답 데이터 저장 완료"); // 로그 추가
        } else {
            return "날씨정보 가져오는데 실패했습니다 ";
        }
        return weatherApiUrl;
    }

    public String insertResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(" <<  responseBody " + responseBody);

        WeatherApiDto response = null;
        try {
            // JSON 문자열 데이터를 WeatherApiDto 객체로 매핑
            response = objectMapper.readValue(responseBody, WeatherApiDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "날씨정보 가져오는데 실패했습니다";
        }

        System.out.println(" <<  WeatherApiDto " + response);

        WeatherEntity weatherEntity = WeatherEntity.builder()
                .cityName(response.getName())
                .lat(response.getCoord().getLat())
                .lon(response.getCoord().getLon())
                .temp(response.getMain().getTemp())
                .feels_like(response.getMain().getFeels_like())
                .temp_max(response.getMain().getTemp_max())
                .temp_min(response.getMain().getTemp_min())
                .humidity(response.getMain().getHumidity())
                .build();
        Optional<WeatherEntity> optionalWeatherEntity = Optional.ofNullable(weatherRepository.findByCityName(response.getName()));
        if (!optionalWeatherEntity.isPresent()) {
            weatherRepository.save(weatherEntity);
        }

        List<Weather> weatherList = response.getWeather()
                .stream()
                .collect(Collectors.toList());

        System.out.println(" <<  weatherList " + weatherList);

        for (Weather item : weatherList) {
            System.out.println(" <<  item " + item);
        }
        return responseBody;
    }






}
