package spring.project.groupware.academy.bus.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bus")
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busRouteAbrv; // 버스번호
    private String busRouteId; // 버스경로 고유번호
    private String corpNm; // 운행회사 전화번호
    private String routeType; // 노선 유형
    private String stStationNm; // 출발역
    private String edStationNm; // 종착,회차역
    private String firstBusTm; // 첫차시간
    private String lastBusTm; // 막차시간
    private String term;
    private String lastBusYn;
    private String lastLowTm;
    private String firstLowTm;
    private String busRouteNm;
    private String length;

//    // 연관 관계 - bus
//    @OneToMany(mappedBy = "busEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<StationEntity> busStations;

}
