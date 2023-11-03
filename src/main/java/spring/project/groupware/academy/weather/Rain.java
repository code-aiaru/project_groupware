package spring.project.groupware.academy.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Rain {

    // 지난 1시간 동안의 강우량, mm
    @JsonProperty("1h")
    private String rain1h;

    // 지난 3시간 동안의 강우량
    @JsonProperty("3h")
    private String rain3h;

}
