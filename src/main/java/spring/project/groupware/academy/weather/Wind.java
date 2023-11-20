package spring.project.groupware.academy.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Wind {

    private String deg;
    private String speed;
    private String gust;
}
