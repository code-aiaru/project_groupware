package spring.project.groupware.academy.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
