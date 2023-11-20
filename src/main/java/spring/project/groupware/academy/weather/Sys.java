package spring.project.groupware.academy.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sys {

    private String country;
    private Long id;
    private String sunrise;
    private String sunset;
    private String type;

}
