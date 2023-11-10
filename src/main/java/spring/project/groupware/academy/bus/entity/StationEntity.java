package spring.project.groupware.academy.bus.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "station")
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private List<String> stationList;

    private String busRouteId;
    private String busRouteNm;
    private String seq;             //순번
    private String section;         //구간ID
    private String station;         //정류소 고유 ID
    private String stationNm;       //정류소명
    //    private String gpsX;            //좌표X (WGS84)
//    private String gpsY;            //좌표Y (WGS84)
    private String direction;       //진행방향
    private String stationNo;       //정류소 번호
    //    private String routeType;       //노선유형  (3:간선, 4:지선, 5:순환, 6:광역)
//    private String beginTm;         //첫차시간
//    private String lastTm;          //막차시간
//    private String posX;            //좌표X (GRS80)
//    private String posY;            //좌표Y (GRS80)
    private String arsId;           //정류소 번호
//    private String transYn;         //(Y:회차, N:회차지아님)	transYn
//    private String trnstnid;        //회차지 ID
//    private String sectSpd;         //구간속도
//    private String fullSectDist;    //구간거리

//    @ManyToOne
//    @JoinColumn(name = "bus_id")
//    private BusEntity busEntity;

}
