package spring.project.groupware.academy.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.project.groupware.academy.weather.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class WeatherChatbotService {

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    public String getWeatherForCity(String message) {
        String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather";
        String apiKey = "31baec95fb6d389a7195e4f5dc84530b";

        String newMessage = message.replace("날씨 현재 ", "");
        log.info("newMessage: " + newMessage);

        String apiUrl = weatherApiUrl + "?q=" + newMessage + "&appid=" + apiKey;
        log.info("apiUrl: " + apiUrl);

        Map<String, String> requestHeaders = new HashMap<>(); // 빈 맵 생성
        // HTTP GET 요청을 보내고 응답을 처리
        String response = OpenApiUtil.get(apiUrl, requestHeaders);

        WeatherApiDto weatherApiDto = processWeatherResponse(response);

        Optional<WeatherEntity> weatherInfo = Optional.ofNullable(weatherRepository.findByCityName(newMessage));
        if(weatherInfo.isPresent()){
            double tempKelvin = Double.parseDouble(weatherInfo.get().getTemp());
            double tempMaxKelvin = Double.parseDouble(weatherInfo.get().getTemp_max());
            double tempMinKelvin = Double.parseDouble(weatherInfo.get().getTemp_min());

            int tempCelsius = (int) (tempKelvin - 273.15);
            int tempMaxCelsius = (int) (tempMaxKelvin - 273.15);
            int tempMinCelsius = (int) (tempMinKelvin - 273.15);

            return "도시: " + weatherInfo.get().getCityName() + "\n"
                    + "현재온도: " + tempCelsius + "°C\n"
                    + "최고온도: " + tempMaxCelsius + "°C\n"
                    + "최저온도: " + tempMinCelsius + "°C\n"
                    + "습도: " + weatherInfo.get().getHumidity() + "%";
        }
        return null;
    }

    public WeatherApiDto processWeatherResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("API 응답 데이터: " + responseBody);

        try {
            WeatherApiDto response = objectMapper.readValue(responseBody, WeatherApiDto.class);
            log.info("WeatherApiDto: " + response);

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

            for (Weather item : weatherList) {
                log.info("Weather: " + item);
            }

            return objectMapper.readValue(responseBody, WeatherApiDto.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

