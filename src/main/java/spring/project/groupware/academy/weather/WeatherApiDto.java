package spring.project.groupware.academy.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeatherApiDto {

    private String base;
    private Clouds clouds;
    private String cod;
    private Coord coord;
    private Long dt;
    private Long id;
    private Main main;
    private String name;
    private Sys sys;
    private String timezone;
    private String visibility;
    private List<Weather> weather;
    private Wind wind;
    private Rain rain;


}
