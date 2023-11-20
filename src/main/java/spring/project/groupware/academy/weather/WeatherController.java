package spring.project.groupware.academy.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;


    // ajax 이용 db 저장
    @PostMapping("/post/weather2")
    public Map<String, String> postWeather(@RequestBody WeatherApiDto weatherApiDto) {
        Map<String, String> response = new HashMap<>();

        if (weatherApiDto != null) {
            WeatherEntity weatherEntity = WeatherEntity.builder()
                    .cityName(weatherApiDto.getName())
                    .lat(weatherApiDto.getCoord().getLat())
                    .lon(weatherApiDto.getCoord().getLon())
                    .temp(weatherApiDto.getMain().getTemp())
                    .feels_like(weatherApiDto.getMain().getFeels_like())
                    .temp_max(weatherApiDto.getMain().getTemp_max())
                    .temp_min(weatherApiDto.getMain().getTemp_min())
                    .humidity(weatherApiDto.getMain().getHumidity())
                    .build();

            WeatherEntity savedEntity = weatherService.insertWeather(weatherEntity);

        }
        return response;
    }


    // 자바 이용 db 저장
    @GetMapping("/api/weather_java")
    public Map<String, String> weatherJava(String q) throws Exception {
        // String q = "Seoul"
        String appid = "31baec95fb6d389a7195e4f5dc84530b";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + q + "&appid=" + appid;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        // OpenApiUtil.get 메서드를 사용하여 API를 호출하고 응답을 가져옴
        String responseBody = OpenApiUtil.get(apiUrl, requestHeaders);
        System.out.printf(" << return " + responseBody);

        // API 응답 데이터를 데이터베이스에 저장하기 위해 insertResponseBody 메서드를 호출
        weatherService.insertResponseBody(responseBody);

        Map<String, String> weather = new HashMap<>();
        weather.put("weather", responseBody);

        return weather;
    }

}
