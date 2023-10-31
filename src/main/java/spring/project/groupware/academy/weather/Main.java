package spring.project.groupware.academy.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Main {

    private String feels_like;
    private String humidity;
    private String pressure;
    private String temp;
    private String temp_max;
    private String temp_min;
    private String sea_level;
    private String grnd_level;

}
