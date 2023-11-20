package spring.project.groupware.academy.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private String temp; // 현재온도
    private String feels_like; // 체감온도
    private String temp_min; // 최저온도
    private String temp_max; // 최고온도
    private String humidity; // 습도
    private String lat; // 위도
    private String lon; // 경도

}
