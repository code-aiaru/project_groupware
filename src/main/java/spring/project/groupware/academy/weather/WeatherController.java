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

}
