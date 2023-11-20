package spring.project.groupware.academy.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;


    public WeatherEntity insertWeather(WeatherEntity weatherEntity) {
        WeatherEntity existWeather = weatherRepository.findByCityName(weatherEntity.getCityName());

        if (existWeather == null) {
            return weatherRepository.save(weatherEntity);
        }else {
            return existWeather;
        }
    }

    public WeatherApiDto insertResponseBody(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(" <<  responseBody " + responseBody);

        WeatherApiDto response = null;
        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(responseBody, WeatherApiDto.class);
        } catch (Exception e) {
            e.printStackTrace();
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
        // list일 경우
        List<Weather> weatherList = response.getWeather()
                .stream()
                .collect(Collectors.toList());

        System.out.println(" <<  weatherList " + weatherList);

        for (Weather item : weatherList) {
            System.out.println(" <<  item " + item);
        }
        return response;
    }

}
