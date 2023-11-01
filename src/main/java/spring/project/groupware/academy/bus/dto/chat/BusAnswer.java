package spring.project.groupware.academy.bus.dto.chat;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BusAnswer {

    private String busRouteAbrv; // 버스번호
    private String busRouteId; // 버스경로 고유번호
    private String corpNm;  // 버스 회사 정보
}
